package org.jeeasy.generate.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.tangzc.mpe.autotable.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 代码生成 模块
 *
 * @author AlpsDDJ
 * @date 2023/12/14
 */
@Data
@Table(value = "gen_module", comment = "模块")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "代码生成模块配置")
public class GenModule extends Model<GenModule> {

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private String id;

    /**
     * 模块名称
     */
    @Schema(description = "模块名称")
    private String name;

    /**
     * 路径
     */
    @Schema(description = "路径")
    private String pkg;

    /**
     * 父包模块名
     */
    @Schema(description = "模块编码")
    private String moduleCode;

    /**
     * Entity 路径
     */
    @Schema(description = "Entity 路径")
    private String entity;

    /**
     * Mapper 路径
     */
    @Schema(description = "Mapper 路径")
    private String mapper;

    /**
     * MapperXml 路径
     */
    @Schema(description = "MapperXml 路径")
    private String mapperXml;

    /**
     * Service 路径
     */
    @Schema(description = "Service 路径")
    private String service;

    /**
     * ServiceImpl 路径
     */
    @Schema(description = "ServiceImpl 路径")
    private String serviceImpl;

    /**
     * controller 路径
     */
    @Schema(description = "Controller 路径")
    private String controller;

    /**
     * webList 路径
     */
    @Schema(description = "webList 路径")
    private String webList;

    /**
     * controller 路径
     */
    @Schema(description = "webModel 路径")
    private String webModel;

    @Schema(description = "表id")
    @TableField(exist = false)
    private String tableId;

}
