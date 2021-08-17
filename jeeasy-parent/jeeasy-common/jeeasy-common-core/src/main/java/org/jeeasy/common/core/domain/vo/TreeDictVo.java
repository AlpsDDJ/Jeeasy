//package org.jeeasy.common.core.domain.vo;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.fasterxml.jackson.annotation.JsonGetter;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.util.List;
//
///**
// * @author AlpsDDJ
// * @date 2021/8/17 14:36
// */
//public class TreeDictVo extends DictVo implements BaseTreeVo<TreeDictVo> {
//
//    @Setter
//    @Getter
//    private List<TreeDictVo> children;
//
//    @Override
//    @JsonGetter
//    public List<TreeDictVo> getChildren() {
//        return children;
//    }
//
//    @Override
//    public boolean hasChildren() {
//        return false;
//    }
//
//    public TreeDictVo(DictVo dictVo){
//        BeanUtil.copyProperties(dictVo, this, true);
//    }
//}
