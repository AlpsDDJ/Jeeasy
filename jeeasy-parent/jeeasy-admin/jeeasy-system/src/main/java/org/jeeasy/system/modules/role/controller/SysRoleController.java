package org.jeeasy.system.modules.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.base.SimpleBaseController;
import org.jeeasy.system.modules.role.entity.SysRole;
import org.jeeasy.system.modules.role.service.SysRoleService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Api(tags = "系统角色")
@RequestMapping("/sys/role")
public class SysRoleController extends SimpleBaseController<SysRoleService, SysRole> {

    @GetMapping
    @ApiOperation(value = "角色列表", notes = "角色列表")
    public R<IPage<SysRole>> list(SysRole entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        return super.query(entity, pageNo, pageSize, req);
    }

    @GetMapping("/{id}")
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

}
