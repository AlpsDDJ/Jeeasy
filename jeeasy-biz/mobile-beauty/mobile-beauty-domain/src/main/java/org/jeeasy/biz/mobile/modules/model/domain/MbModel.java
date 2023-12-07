package org.jeeasy.biz.mobile.modules.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.EnableFlagEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * FastNote用户
 *
 * @author wei.yang
 * @date 2022-09-11 18:44
 */
@Data
@Table(value = "mb_model", comment = "型号表")
@Accessors(chain = true)
@Schema(description = "品牌")
public class MbModel extends Model<MbModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID", example = "ID")
    private String id;

    @Schema(description = "型号")
    private String model;

    @Schema(description = "品牌编码")
    @Dict("#mb_brand")
    private String brandCode;

    @Schema(description = "型号编码")
    private String code;

    @Schema(description = "启用标记")
    @Dict(dictEnum = EnableFlagEnum.class)
    @TableField(fill = FieldFill.INSERT)
    private Integer enableFlag;


    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @Schema(description = "备注")
    private String remark;

    @TableLogic
    @Schema(description = "删除标记")
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;
}
