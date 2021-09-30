package org.jeeasy.common.core.domain.vo;

import java.util.List;

/**
 * BaseTree  树形结构数据基础模型
 *
 * @author AlpsDDJ
 * @date 2021/8/17 9:49
 */
public interface BaseTree<T> {
    /**
     * 获取children
     *
     * @return {@link List}<{@link T}>
     */
    List<T> getChildren();
}
