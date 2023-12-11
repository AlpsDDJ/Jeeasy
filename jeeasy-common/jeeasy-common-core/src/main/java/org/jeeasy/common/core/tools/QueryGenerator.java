package org.jeeasy.common.core.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 条件查询构造器
 *
 * @author mobie
 */
public class QueryGenerator {

    /**
     * 数字类型字段，拼接此后缀 接受多值参数
     */
    private static final String STAR = "*";
    private static final char COMMA = ',';

//    public static <T> QueryWrapper<T> createWrapper(T entity, Map<String, String[]> params) {
//        return new QueryWrapper<T>();
//    }

    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz) {
        return new QueryWrapper<T>();
    }

    /**
     * 根据给定的类和参数创建一个QueryWrapper对象。
     *
     * @param clazz  给定的类
     * @param params 给定的参数映射
     * @param <T>    给定的类的类型参数
     * @return 创建的QueryWrapper对象
     */
    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz, Map<String, String[]> params) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();

        // 遍历参数映射
        params.forEach((key, value) -> {
            // 获取指定字段
            Field field = ReflectUtil.getField(clazz, key);
            // 判断字段是否存在且不包含TableField注解
            if (Objects.nonNull(field)) {
                TableField tableFieldAnnotation = AnnotationUtils.getAnnotation(field, TableField.class);
                if (Objects.isNull(tableFieldAnnotation) || !tableFieldAnnotation.exist()) {
                    // 解析查询参数
                    parseQueryParameters(wrapper, clazz, key, value);
                }
            }
        });
        return wrapper;
    }


    /**
     * 解析查询参数
     *
     * @param clazz     实体类Class
     * @param fieldName 实体类属性名称
     * @param params    前端传来的原始参数
     * @param <T>
     * @return
     */
    private static <T> QueryWrapper<T> parseQueryParameters(QueryWrapper<T> wrapper, Class<T> clazz, String fieldName, String... params) {
        // 遍历参数列表
        for (String param : params) {
            // 获取属性的列名
            String columnName = getColumnName(fieldName, clazz);
            // 获取参数的值
            String val = getParamValue(param);
            // 如果值为空，则结束当前参数的处理
            if (StrUtil.isEmpty(val)) {
                break;
            }
            // 如果参数以"!"开头，则执行不等于操作
            if (param.startsWith("! ")) {
                wrapper.ne(columnName, val);
            }
            // 如果参数以">"开头，则执行大于操作
            else if (param.startsWith("> ")) {
                wrapper.gt(columnName, val);
            }
            // 如果参数以">= "开头，则执行大于等于操作
            else if (param.startsWith(">= ")) {
                wrapper.ge(columnName, val);
            }
            // 如果参数以"< "开头，则执行小于操作
            else if (param.startsWith("< ")) {
                wrapper.lt(columnName, val);
            }
            // 如果参数以"<= "开头，则执行小于等于操作
            else if (param.startsWith("<= ")) {
                wrapper.le(columnName, val);
            }
            // 如果参数以"in "开头，则执行属于某个范围内的操作
            else if (param.startsWith("in ")) {
                // 将值列表按照","进行分割，并转为字符串列表
                List<String> values = StrUtil.split(val, COMMA, true, true);
                wrapper.in(columnName, values);
            }
            // 如果参数以"!* "开头，则执行不包含某个值的操作
            else if (param.startsWith("!* ")) {
                wrapper.notLike(columnName, val);
            }
            // 如果参数包含"%"则执行模糊查询操作
            else if (param.contains(STAR)) {
                // 将like操作应用到指定的列和值上
                putLike(wrapper, columnName, param, val);
            }
            // 否则执行等于操作
            else {
                wrapper.eq(columnName, val);
            }
        }
        // 返回处理后的查询条件
        return wrapper;
    }


    /**
     * 获取数据库表字段名称
     *
     * @param fieldName 实体类属性名称
     * @param clazz     实体类Class
     * @param <T>
     * @return
     */
    public static <T> String getColumnName(String fieldName, Class<T> clazz) {
        // 将实体类属性名称转换为下划线命名法
        String colName = StrUtil.toUnderlineCase(fieldName);
        // 获取实体类的指定属性
        Field field = ReflectUtil.getField(clazz, fieldName);
        // 获取指定属性上的@TableField注解
        TableField annotation = field.getAnnotation(TableField.class);
        // 如果@TableField注解不为空
        if (BeanUtil.isNotEmpty(annotation)) {
            // 注解的value属性值
            String value = annotation.value();
            // 注解的exist属性值
            boolean exist = annotation.exist();
            // 如果value属性非空且exist属性为false
            if (StrUtil.isNotEmpty(value) && !exist) {
                // 更新字段名称为value属性值
                colName = value;
            }
        }
        // 返回最终的字段名称
        return colName;
    }


    /**
     * 模糊查询
     *
     * @param wrapper    数据库查询条件封装类
     * @param columnName 数据库表字段名称
     * @param param      前端传来的原始参数
     * @param value      格式化后的查询参数
     */
    private static void putLike(QueryWrapper<?> wrapper, String columnName, String param, String value) {
        if (param.startsWith(STAR) && param.endsWith(STAR)) {
            wrapper.like(columnName, value);
        } else {
            if (param.startsWith(STAR)) {
                wrapper.likeLeft(columnName, value);
            }
            if (param.endsWith(STAR)) {
                wrapper.likeRight(columnName, value);
            }
        }
    }


    /**
     * 格式化查询参数
     *
     * @param param 前端传来的原始参数
     * @return 格式化后的查询参数
     */
    private static String getParamValue(String param) {
        String p = param.replaceAll("(^>= )|(^<= )|(^> )|(^< )|(^in )|(^!\\* )|(^! )|(\\*)", "");
        return StrUtil.trim(p);

    }

}
