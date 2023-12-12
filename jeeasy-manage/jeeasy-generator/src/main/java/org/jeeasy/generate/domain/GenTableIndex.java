package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.generate.emuns.IndexTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成 表索引
 *
 * @author AlpsDDJ
 * @description 菜单权限
 * @since 2021-01-14 13:52:05
 */
@Data
@Table(value = "gen_table_index", comment = "表索引")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "代码生成索引信息")
public class GenTableIndex extends Model<GenTableIndex> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private String tableId;

    /**
     * 索引名称
     */
    @Schema(description = "索引名称")
    private String indexName;

    /**
     * 索引字段
     */
    @Schema(description = "索引字段")
    private String indexFields;

    /**
     * 索引类型
     */
    @Schema(description = "索引类型")
    @Dict(dictEnum = IndexTypeEnum.class)
    private String indexType;


    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建日期
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    /**
     * 更新日期
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "删除状态")
    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private Integer delFlag;

    /**
     * 所属部门
     */
    @Schema(description = "所属部门")
    @TableField(fill = FieldFill.INSERT)
    private String sysOrgCode;
}
