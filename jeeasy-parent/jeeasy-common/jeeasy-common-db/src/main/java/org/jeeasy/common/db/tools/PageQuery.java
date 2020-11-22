package org.jeeasy.common.db.tools;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * PageQuery: TODO
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/22 21:18
 */
public class PageQuery<T, M> extends Page<T> {
    @Getter
    @Setter
    private M model;

    private Class<T> entityClass;

    public PageQuery() {
//        Type type = getClass().getGenericSuperclass();
//        ParameterizedType ptype=(ParameterizedType)type;
//        Type[] types = ptype.getActualTypeArguments();
//        entityClass=(Class<T>) types[0];
        Method method = ReflectUtil.getMethod(getClass(), "tClass");
        Type returnType = TypeUtil.getReturnType(method);
        entityClass = (Class<T>) returnType;
//        entityClass.get
//        ReflectUtil.getM
    }

    private T tClass(){
        return null;
    }

    public QueryWrapper<T> wrapper(){
        return QueryGenerator.createWrapper(entityClass);
    }
}
