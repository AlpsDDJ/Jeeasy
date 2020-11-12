package org.jeeasy.common.core.api;

/**
 * @author AlpsDDJ
 * @date 2020/11/12
 */
public interface CacheApi {
    boolean hasKey(Object key);

    void boundValueOps(Object key);
}
