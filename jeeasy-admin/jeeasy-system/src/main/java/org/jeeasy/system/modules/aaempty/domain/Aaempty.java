package org.jeeasy.system.modules.aaempty.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.Dict;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.common.core.enums.EnableFlagEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据字典项
 *
 * @author AlpsDDJ
 * @date 2020/11/23 10:50
 */
@Data
@Accessors(chain = true)
public class Aaempty implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id", notes = "ID")
    private String id;

    @ApiModelProperty(value = "dictId", notes = "字典ID")
    private String dictId;

    @ApiModelProperty(value = "itemText", notes = "字典项文本")
    private String itemText;

    @ApiModelProperty(value = "itemValue", notes = "字典项值")
    private String itemValue;

    @ApiModelProperty(value = "parentId", notes = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "description", notes = "描述")
    private String description;

    @ApiModelProperty(value = "sortOrder", notes = "排序")
    private Double sortOrder;

    @Dict(dictEnum = EnableFlagEnum.class)
    @ApiModelProperty(value = "enableFlag", notes = "启用状态")
    private Integer enableFlag;

    @ApiModelProperty(value = "createBy", notes = "创建人")
    private String createBy;

    @ApiModelProperty(value = "createTime", notes = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "updateBy", notes = "更新人")
    private String updateBy;

    @ApiModelProperty(value = "updateTime", notes = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "allowDelete", notes = "允许删除")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowDelete;

    @ApiModelProperty(value = "allowUpdate", notes = "允许修改")
    @Dict(dictEnum = BooleanEnum.class)
    private Integer allowUpdate;

}
