package org.jeeasy.biz.fastnote.modules.note.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * FastNote用户
 *
 * @author wei.yang
 * @date 2022-09-11 18:44
 */
@Data
@TableName("fn_note")
@Accessors(chain = true)
@Schema(description = "FastNote笔记")
public class FnNote extends Model<FnNote> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID", example = "ID")
    private String id;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "记录日期")
    private LocalDate noteDate;

    @Schema(description = "用户ID")
    private String ftMemberId;

    @TableLogic
    @Schema(description = "删除标记")
    private String delFlag;

    @Schema(description = "标签")
    private String labels;


}
