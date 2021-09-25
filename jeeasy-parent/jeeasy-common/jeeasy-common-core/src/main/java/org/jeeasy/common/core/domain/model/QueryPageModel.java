package org.jeeasy.common.core.domain.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Getter;
import lombok.Setter;
import org.jeeasy.common.core.tools.QueryGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AlpsDDJ
 * @date 2021/8/9 16:31
 */
@Getter
@Setter
public class QueryPageModel implements QueryModel {


    private int size = 10;
    private int current = 1;
    private String sort;

    public boolean hasSort() {
        return BeanUtil.isNotEmpty(sort) && !StrUtil.EMPTY_JSON.equals(sort);
    }

    public <T> Page<T> getPage(Class<T> tClass){
        Page<T> page = new Page<>(this.current, this.size);
        Map<String, String> sortMap = new HashMap<>();
        sortMap = JSONUtil.toBean(sort, sortMap.getClass());
        if(MapUtil.isNotEmpty(sortMap)){
            List<OrderItem> orderItems = new ArrayList<>();

            sortMap.forEach((key, val) -> {
                String columnName = QueryGenerator.getColumnName(key, tClass);
                orderItems.add(new OrderItem(columnName, "ascend".equals(val)));
            });

            page.setOrders(orderItems);
        } else {
            this.sort = StrUtil.EMPTY;
        }

        return page;
    }
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
