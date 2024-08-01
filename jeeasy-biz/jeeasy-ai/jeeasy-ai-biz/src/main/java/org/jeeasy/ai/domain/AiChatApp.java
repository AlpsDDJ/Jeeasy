package org.jeeasy.ai.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.ColumnType;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.common.core.enums.EnableFlagEnum;

import java.time.LocalDateTime;

/**
 * AI 应用
 *
 * @author wei.yang
 * @date 2024-07-29 13:04:49
 */
@Data
@Table(value = "ai_chat_app", comment = "AI 应用")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "AI 应用")
public class AiChatApp extends Model<AiChatApp> {


    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    @TableField(value = "id")
    private String id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(value = "update_time")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    @TableField(value = "del_flag")
    private Integer delFlag;

    /**
     * 版本
     */
    @Schema(description = "版本")
    @TableField(value = "version")
    private Integer version;

    /**
     * 启用标记
     */
    @Schema(description = "启用标记")
    @TableField(value = "enable_flag")
    @Dict(dictEnum = EnableFlagEnum.class)
    private Integer enableFlag;

    /**
     * 应用名称
     */
    @Schema(description = "应用名称")
    @TableField(value = "name")
    private String name;

    /**
     * 应用名称
     */
    @Schema(description = "应用代码")
    @TableField(value = "code")
    private String code;

    /**
     * 提示词
     */
    @Schema(description = "提示词")
    @TableField(value = "prompt")
    @ColumnType(value = "TEXT")
    private String prompt;

    /**
     * 模型
     */
    @Schema(description = "模型")
    @TableField(value = "model")
    private String model;

}
