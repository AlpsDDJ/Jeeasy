package org.jeeasy.system.modules.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.base.SimpleBaseController;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Api(tags = "系统用户")
@RequestMapping("/sys/user")
public class SysUserController extends SimpleBaseController<ISysUserService, SysUser> {

    @GetMapping
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public R<Page<SysUser>> list(SysUser entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return super.query(entity, pageNo, pageSize, req);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查找用户", notes = "根据ID查找用户")
    public R<SysUser> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "编辑用户", notes = "编辑用户")
    public R<?> edit(@RequestBody SysUser entity) {
        return super.update(entity);
    }

    @PostMapping
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public R<?> add(@RequestBody SysUser entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public R<?> delete(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    public R<?> deleteBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

}
