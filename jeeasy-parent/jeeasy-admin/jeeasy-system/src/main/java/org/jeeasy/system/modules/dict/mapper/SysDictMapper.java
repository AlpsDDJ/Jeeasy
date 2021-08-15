package org.jeeasy.system.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 根据上级code获取字典列表
     * @param parentCode
     * @return
     */
    List<SysDict> queryByParentCode(@Param("code") String parentCode);

    /**
     * 根据 TableDict 获取字典列表
     * @param tableDict
     * @return
     */
    List<SysDict> queryByTableDict(@Param("tableDict") SysTableDict tableDict);


    /**
     *
     * @param parentCode 上级 dictCode
     * @param code 待查询的 dictCode
     * @return
     */
    SysDict getOneByParentCodeAndDictCode(@Param("parentCode") String parentCode, @Param("code") String code);
}
