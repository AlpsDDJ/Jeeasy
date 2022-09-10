package org.jeeasy.system.modules.premission.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 *
 * @author AlpsDDJ
 * @date 2020/11/24 16:01
 */
@Data
@Schema(description = "菜单数据")
@Accessors(chain = true)
public class MenuVo {

    @Schema(description = "ID")
    private String id;

    @Schema(description = "上级菜单id")
    private String parentId;

    @Schema(description = "菜单名称")
    private String name;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "组件")
    private String component;

    @Schema(description = "打开方式")
    private String target;

    @Schema(description = "子菜单")
    private List<MenuVo> children;
}
