/**
 * 字典服务接口，扩展自IService<SysDict>，提供针对SysDict实体的业务操作。
 */
package org.jeeasy.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 10:01
 */
public interface SysDictService extends IService<SysDict> {

    /**
     * 根据父级代码查询字典列表。
     * <p>
     * 用于获取特定父级代码下的所有字典项，支持层级关系的字典查询。
     *
     * @param parentCode 父级代码，用于定位字典的层级关系。
     * @return 符合条件的字典列表。
     */
    List<SysDict> queryByParentCode(String parentCode);

    /**
     * 根据表字典配置和异步标志查询字典列表。
     * <p>
     * 此方法根据SysTableDict对象中的配置信息，查询相应的字典数据。
     * 异步标志用于决定是否以异步方式执行查询，提高系统的响应性能。
     *
     * @param tableDict 表字典对象，包含查询所需配置信息。
     * @param async     异步查询标志，true表示异步查询，false表示同步查询。
     * @return 字典值列表，具体类型为DictVo的子类。
     */
    List<? extends DictVo> queryByTableDict(SysTableDict tableDict, boolean async);

    /**
     * 根据表字典配置和指定值查询字典列表。
     * <p>
     * 该方法用于根据特定的表字典配置和一个值，查询相关的字典列表。
     * 主要用于在前端展示时，根据已选择的值，加载相应的下拉选项。
     *
     * @param tableDict 表字典对象，包含查询所需配置信息。
     * @param value     指定的值，用于查询相关的字典项。
     * @return 符合条件的字典值列表。
     */
    List<DictVo> getParent(SysTableDict tableDict, String value);
}
