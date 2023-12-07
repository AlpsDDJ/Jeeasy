package org.jeeasy.biz.mobile.modules.brand.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Column;
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
//@TableName("mb_brand")
@Table(value = "mb_brand", comment = "品牌表")
@Accessors(chain = true)
@Schema(description = "品牌")
public class MbBrand extends Model<MbBrand> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID", example = "ID")
    private String id;

    @Schema(description = "品牌名称")
    @Column(length = 50, comment = "品牌名称")
    private String brandName;

    @Schema(description = "品牌编码")
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
