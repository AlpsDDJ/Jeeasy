package org.jeeasy.system.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.common.core.domain.vo.TableDictVo;
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
    List<DictVo> queryByTableDict(@Param("tableDict") SysTableDict tableDict);


    /**
     *
     * @param parentCode 上级 dictCode
     * @param code 待查询的 dictCode
     * @return
     */
    SysDict getOneByParentCodeAndDictCode(@Param("parentCode") String parentCode, @Param("code") String code);


    /**
     *
     * @param tableDict SysTableDict
     * @param code 待查询的 dictCode
     * @return
     */
    SysDict getOneByTableDictAndCode(@Param("tableDict") TableDictVo tableDict, @Param("code") String code);
}
