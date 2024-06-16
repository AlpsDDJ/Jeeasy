package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Column;
import com.tangzc.mpe.autotable.annotation.ColumnId;
import com.tangzc.mpe.autotable.annotation.Table;
import com.tangzc.mpe.autotable.strategy.mysql.data.MysqlTypeConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成模板
 *
 * @author wei.yang
 * @date 2023-12-17 14:56:27
 */
@Data
@Table(value = "gen_template", comment = "代码生成模板")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "代码生成模板")
public class GenTemplate extends Model<GenTemplate> {


    /**
     * ID
     */
    @Schema(description = "ID")
    @ColumnId(value = "id", comment = "ID", mode = IdType.ASSIGN_ID, length = 32)
    private String id;

    /**
     * 文件名
     */
    @Schema(description = "文件名")
    @Column(value = "file_name")
    private String fileName;

    /**
     * 模板内容
     */
    @Schema(description = "模板内容")
    @Column(value = "context", type = MysqlTypeConstant.TEXT)
    //@TableField(typeHandler = ClobTypeHandler.class, jdbcType = JdbcType.CLOB)
    private String context;

    /**
     * 模板类型
     */
    @Schema(description = "模板类型")
    @Column(value = "type")
    private String type;

    /**
     * 启用标记
     */
    @Schema(description = "启用标记")
    @Column(value = "enable_flag")
    private Integer enableFlag;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @Column(value = "remark")
    private String remark;

}
