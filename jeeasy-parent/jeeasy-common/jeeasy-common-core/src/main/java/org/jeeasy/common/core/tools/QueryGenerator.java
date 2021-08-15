package org.jeeasy.common.core.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

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

    public static <T> QueryWrapper<T> createWrapper(T entity, Map<String, String[]> params) {
        return new QueryWrapper<T>();
    }

    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz) {
        return new QueryWrapper<T>();
    }

    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz, Map<String, String[]> params) {
        AtomicReference<QueryWrapper<T>> wrapper = new AtomicReference<>();

        params.forEach((key, value) -> {
            if (ReflectUtil.hasField(clazz, key)) {
                wrapper.set(parseQueryParameters(clazz, key, value));
            }
        });

        return wrapper.get();
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
    private static <T> QueryWrapper<T> parseQueryParameters(Class<T> clazz, String fieldName, String... params) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (String param : params) {
            String columnName = getColumnName(fieldName, clazz);
            String val = getParamValue(param);
            if (StrUtil.isEmpty(val)) {
                break;
            }
            if (param.startsWith("! ")) {
                wrapper.ne(columnName, val);
            } else if (param.startsWith("> ")) {
                wrapper.gt(columnName, val);
            } else if (param.startsWith(">= ")) {
                wrapper.ge(columnName, val);
            } else if (param.startsWith("< ")) {
                wrapper.lt(columnName, val);
            } else if (param.startsWith("<= ")) {
                wrapper.le(columnName, val);
            } else if (param.startsWith("in ")) {
                List<String> values = StrUtil.split(val, COMMA, true, true);
                wrapper.in(columnName, values);
            } else if (param.startsWith("!* ")) {
                wrapper.notLike(columnName, val);
            } else if (param.contains(STAR)) {
                putLike(wrapper, columnName, param, val);
            } else {
                wrapper.eq(columnName, val);
            }
        }
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
    private static <T> String getColumnName(String fieldName, Class<T> clazz) {
        String colName = StrUtil.toUnderlineCase(fieldName);
        Field field = ReflectUtil.getField(clazz, fieldName);
        TableField annotation = field.getAnnotation(TableField.class);
        if (BeanUtil.isNotEmpty(annotation)) {
            String value = annotation.value();
            boolean exist = annotation.exist();
            if (StrUtil.isNotEmpty(value) && !exist) {
                colName = value;
            }
        }
        return colName;
    }

    /**
     * 模糊查询
     *
     * @param wrapper
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

    public static void main(String[] args) {
        String str = " aaa , ";
        System.out.println(StrUtil.split(str, COMMA, true, true));
    }

}
