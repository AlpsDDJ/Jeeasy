package org.jeeasy.system.modules.premission.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("菜单数据")
@Accessors(chain = true)
public class MenuVo {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单路径")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("打开方式")
    private String target;

    @ApiModelProperty("子菜单")
    private List<MenuVo> children;
}
