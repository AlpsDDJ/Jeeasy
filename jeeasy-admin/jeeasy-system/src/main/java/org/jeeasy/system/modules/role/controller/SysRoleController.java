package org.jeeasy.system.modules.role.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.jeeasy.system.modules.role.domian.SysRole;
import org.jeeasy.system.modules.role.domian.SysRolePermission;
import org.jeeasy.system.modules.role.domian.model.RolePermissionsModel;
import org.jeeasy.system.modules.role.service.SysRolePermissionService;
import org.jeeasy.system.modules.role.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Api(tags = "系统角色")
@RequestMapping("/sys/role")
public class SysRoleController extends SimpleBaseController<SysRoleService, SysRole> {

    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private SysRolePermissionService rolePermissionService;

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "角色列表", notes = "角色列表")
    public R<IPage<SysRole>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.query(queryPageModel, req, SysRole.class);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找角色", notes = "根据ID查找角色")
    public R<SysRole> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "编辑角色", notes = "编辑角色")
    public R<?> edit(@RequestBody SysRole entity) {
        return super.update(entity);
    }

    @PostMapping
    @ApiOperation(value = "添加角色", notes = "添加角色")
    public R<?> add(@RequestBody SysRole entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除角色", notes = "根据ID删除角色")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

    @GetMapping("/permissions/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找角色权限", notes = "根据ID查找角色权限")
    public R<List<SysPermission>> permissions(@PathVariable("id") String id) {
        return R.ok(permissionService.queryByRoleId(id));
    }

    @PostMapping("/permissions")
    @DictTranslation
    @ApiOperation(value = "保存角色权限", notes = "保存角色权限")
    public R<?> savePermissions(@RequestBody RolePermissionsModel model) {
//        return R.ok(permissionService.queryByRoleId(id));
        String roleId = model.getRoleId();
        rolePermissionService.remove(new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId));
        List<SysRolePermission> rps = new ArrayList<>();
        Arrays.stream(model.getPermissions()).forEach(p -> {
            SysRolePermission rp = new SysRolePermission();
            rp.setPermissionId(p).setRoleId(roleId);
            rps.add(rp);
        });
        return new R<>().setSuccess(rolePermissionService.saveBatch(rps));
    }

}
