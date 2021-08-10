package org.jeeasy.common.db.tools;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeeasy.common.db.model.QueryPageModel;

import java.util.Map;

/**
 * 条件查询构造器
 * @author mobie
 */
public class QueryGenerator {
    public static <T> QueryWrapper<T> createWrapper(T entity, Map<String, String[]> params) {
        return new QueryWrapper<T>();
    }

    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz, Map<String, String[]> params) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();


        params.forEach((key, value) -> {
            if (!ReflectUtil.hasField(QueryPageModel.class, key)) {

            }
        });


        return wrapper;
    }

    public static <T> QueryWrapper<T> createWrapper(Class<T> clazz) {
        return new QueryWrapper<T>();
    }


}
