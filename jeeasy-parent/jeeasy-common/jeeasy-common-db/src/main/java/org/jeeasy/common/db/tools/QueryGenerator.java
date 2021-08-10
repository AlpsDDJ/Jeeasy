package org.jeeasy.common.db.tools;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 条件查询构造器
 * @author mobie
 */
public class QueryGenerator {

    private static final String BEGIN = "_begin";
    private static final String END = "_end";
    /**
     * 数字类型字段，拼接此后缀 接受多值参数
     */
    private static final String MULTI = "_MultiString";
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
                wrapper.set(putParam(clazz, key, value));
            }
        });

        return wrapper.get();
    }

    private static <T> QueryWrapper<T> putParam(Class<T> clazz, String field, String ...params){
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        for (String param : params) {
            String fieldName = StrUtil.toUnderlineCase(field);
            String val = getParamValue(param);
            if(StrUtil.isEmpty(val)){
                break;
            }
            if(param.contains(STAR)){
                putLike(wrapper, field, param);
            } else if(param.startsWith("!")){
                wrapper.ne(fieldName, val);
            } else if(param.startsWith("> ")){
                wrapper.gt(fieldName, val);
            } else if(param.startsWith(">= ")){
                wrapper.ge(fieldName, val);
            } else if(param.startsWith("< ")){
                wrapper.lt(fieldName, val);
            } else if(param.startsWith("<= ")){
                wrapper.le(fieldName, val);
            } else if(param.startsWith("in ")){
                List<String> values = StrUtil.split(val, COMMA, true, true);
                wrapper.in(fieldName, values);
            } else {
                wrapper.eq(fieldName, val);
            }
        }
        return wrapper;
    }

    private static <T> void putLike(QueryWrapper<T> wrapper, String field, String value){
        String val = value.replaceAll("\\" + STAR, "");
        String fieldName = StrUtil.toUnderlineCase(field);
        if(value.startsWith(STAR) && value.endsWith(STAR)){
            wrapper.like(fieldName, val);
        } else {
            if(value.startsWith(STAR)){
                wrapper.likeLeft(fieldName, val);
            }
            if(value.endsWith(STAR)){
                wrapper.likeRight(fieldName, val);
            }
        }
    }

    private static String getParamValue(String param){
        String p = param.replaceAll("(\\*)|(^>= )|(^<= )|(^> )|(^< )|(^in )", "");
        return StrUtil.trimStart(StrUtil.trimEnd(p));

    }

    public static void main(String[] args) {
        String paramValue = getParamValue(">= 11");
        System.out.println(StrUtil.isNotEmpty(""));
    }


}
