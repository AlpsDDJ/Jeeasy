package org.jeeasy.common.db.tools;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class QueryGenerator {
    public static<T> QueryWrapper<T> createWrapper(T entity, Map<String, String[]> params) {
        return new QueryWrapper<T>();
    }
    public static<T> QueryWrapper<T> createWrapper(Class<T> clazz) {
        return new QueryWrapper<T>();
    }
}
