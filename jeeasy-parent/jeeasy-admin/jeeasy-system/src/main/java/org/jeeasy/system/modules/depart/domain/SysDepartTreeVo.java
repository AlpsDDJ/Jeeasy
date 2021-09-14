//package org.jeeasy.system.modules.depart.domain;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.fasterxml.jackson.annotation.JsonGetter;
//import lombok.Getter;
//import lombok.Setter;
//import org.jeeasy.common.core.domain.vo.BaseTreeVo;
//import org.jeeasy.system.enums.depart.OrgTypeEnum;
//
//import java.util.List;
//
///**
// * @author AlpsDDJ
// * @date 2021/8/17 9:54
// */
//public class SysDepartTreeVo extends SysDepart implements BaseTreeVo<SysDepartTreeVo> {
//
//    @Setter
//    @Getter
//    private List<SysDepartTreeVo> children;
//
//    @Override
//    public List<SysDepartTreeVo> getChildren() {
//        return this.children;
//    }
//
//    @Override
//    @JsonGetter
//    public boolean hasChildren() {
//        Integer orgType = this.getOrgType();
//        return OrgTypeEnum.TOP.getValue().equals(orgType);
//    }
//
//    public SysDepartTreeVo(SysDepart depart){
//        BeanUtil.copyProperties(depart, this, true);
//    }
//}
