//package org.jeeasy.system.modules.dept.domain;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.fasterxml.jackson.annotation.JsonGetter;
//import lombok.Getter;
//import lombok.Setter;
//import org.jeeasy.common.core.domain.vo.BaseTreeVo;
//import org.jeeasy.system.enums.dept.OrgTypeEnum;
//
//import java.util.List;
//
///**
// * @author AlpsDDJ
// * @date 2021/8/17 9:54
// */
//public class SysDeptTreeVo extends SysDept implements BaseTreeVo<SysDeptTreeVo> {
//
//    @Setter
//    @Getter
//    private List<SysDeptTreeVo> children;
//
//    @Override
//    public List<SysDeptTreeVo> getChildren() {
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
//    public SysDeptTreeVo(SysDept dept){
//        BeanUtil.copyProperties(dept, this, true);
//    }
//}
