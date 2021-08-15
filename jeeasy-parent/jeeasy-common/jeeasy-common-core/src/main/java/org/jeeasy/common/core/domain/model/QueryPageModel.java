package org.jeeasy.common.core.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author AlpsDDJ
 * @date 2021/8/9 16:31
 */
@Getter
@Setter
public class QueryPageModel implements QueryModel {


    private int size = 10;
    private int current = 1;
    private String[] orders;
//    private String query;
//    private Map<String, Object> params;

//    public <T> T getQueryObj(Class<T> c){
//        return BeanUtil.mapToBean(this.params, c, true);
//    }

//    public void setQuery(Map<String, Object> query) {
//        this.query = query;
//        Method method = ReflectUtil.getMethod(this.getClass(), "getQueryObj");
//        Type returnType = TypeUtil.getReturnType(method);
//        Class<T> aClass = (Class<T>) TypeUtil.getClass(returnType);
//
//        queryObj = BeanUtil.mapToBean(query, );
//    }
//
//    private T queryObj;

}
