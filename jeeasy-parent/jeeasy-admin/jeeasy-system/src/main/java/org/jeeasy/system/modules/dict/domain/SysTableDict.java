package org.jeeasy.system.modules.dict.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @author mobie
 * @TableName sys_table_dict
 */
@TableName(value ="sys_table_dict")
@Data
public class SysTableDict implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 查询名称
     */
    private String queryName;

    /**
     * 查询表名
     */
    private String tableName;

    /**
     * 数据源名称
     */
    private String databaseName;

    /**
     * 值字段
     */
    private String valueColumn;

    /**
     * 名称字段
     */
    private String nameColumn;

    /**
     * 启用标记 0: 未启用 1： 启用
     */
    private Boolean enableFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0: 未删除 1: 已删除
     */
    private Boolean delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}