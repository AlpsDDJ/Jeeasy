package org.jeeasy.common.core.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author AlpsDDJ
 * @date 2021/8/9 16:31
 */
@Getter
@Setter
public class QueryPageModel<T> implements QueryModel {


    private int size = 10;
    private int current = 1;
    private String[] orders;

    private T query;

}
