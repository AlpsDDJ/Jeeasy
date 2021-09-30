package org.jeeasy.common.core.domain.vo;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jeeasy.common.core.enums.BooleanEnum;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/17 14:36
 */
@NoArgsConstructor
public class TreeDictVo extends DictVo {

    @Setter
    @Getter
    private List<TreeDictVo> children;

    @Setter
    @Getter
    private String parentId;

    @Setter
    @Getter
    @JsonIgnore
    private Integer leaf;

    @JsonGetter("isLeaf")
    public Boolean isLeaf(){
        return BooleanEnum.yes(leaf);
    }

    public TreeDictVo(DictVo dictVo){
        BeanUtil.copyProperties(dictVo, this, true);
    }
}
