package org.jeeasy.common.core.domain.vo;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.common.core.enums.IDictEnum;

import java.io.Serializable;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 14:45
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class DictVo implements Serializable {

    private static final long serialVersionUID = 1L;

//    @ApiModelProperty(value = "dictCode", notes = "字典值")
    private Object dictCode;
//    @ApiModelProperty(value = "dictName", notes = "字典名称")
    private String dictName;

    public DictVo(IDictEnum<?> dictEnum){
        this.dictCode = dictEnum.getValue();
        this.dictName = dictEnum.getText();
    }

    @JsonIgnore
    private Integer leaf;

    @JsonGetter
    public Boolean isLeaf(){
        return BooleanEnum.yes(leaf);
    }
}
