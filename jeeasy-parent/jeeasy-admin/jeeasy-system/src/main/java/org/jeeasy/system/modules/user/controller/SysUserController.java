package org.jeeasy.system.modules.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.base.SimpleBaseController;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.model.ChangePasswordByOldPasswordModel;
import org.jeeasy.system.modules.user.model.UserInfoModel;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Slf4j
@RestController
@Api(tags = "系统用户")
@RequestMapping("/sys/user")
public class SysUserController extends SimpleBaseController<SysUserService, SysUser> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public R<IPage<SysUser>> list(SysUser entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
//        log.info(entity.getStatus().toString());
        R<IPage<SysUser>> query = super.query(entity, pageNo, pageSize, req);
        return query;
    }

//    @GetMapping("/page")
//    @ApiOperation(value = "用户列表", notes = "用户列表")
//    public R<IPage<SysUser>> page(PageQuery<SysUser, SysUser> pageQuery, HttpServletRequest req) {
////        log.info(entity.getStatus().toString());
//        R<IPage<SysUser>> query = super.page(pageQuery.getModel(), pageQuery, req);
//        return query;
//    }

    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找用户", notes = "根据ID查找用户")
    public R<SysUser> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PostMapping
    @ApiOperation(value = "添加用户", notes = "添加用户")
    public R<?> add(@RequestBody UserInfoModel model) {
        SysUser sysUser = SysUserUtil.create(model.getUser()).initSaltAndPassword();
        baseService.addUserWithRole(sysUser, model.getRoles());
        return R.ok().setMessage("添加成功");
    }

    @PutMapping
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public R<?> edit(@RequestBody UserInfoModel model) {
        baseService.editUserWithRole(model.getUser(), model.getRoles());
        return R.ok().setMessage("修改成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户", notes = "根据ID删除用户")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

    /**
     * 通过旧密码验证修改新密码
     *
     * @param model
     * @return
     */
    @PutMapping("/changePasswordByOldPassword")
    @ApiOperation(value = "修改用户密码", notes = "通过旧密码验证修改新密码")
    public R<?> changePasswordByOldPassword(@RequestBody ChangePasswordByOldPasswordModel model) {
        SysUser sysUser = this.baseService.getById(model.getId());
        SysUserUtil sysUserUtil = SysUserUtil.create(sysUser);
        if (sysUserUtil.checkPassword(model.getOldPassword())) {
            sysUserUtil.changePassword(model.getNewPassword());
            this.baseService.updateById(sysUser);
            return R.ok("密码修改成功");
        } else {
            return R.error("密码错误");
        }
    }

    /**
     * 重置用户密码为初始密码 [123456]
     *
     * @author AlpsDDJ
     * @date 2020/11/21 21:50
     * @param id
     * @return {@link R<?>}
     */
    @PutMapping("/resetPassword")
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码为初始密码")
    public R<?> resetPassword(@RequestBody String id) {
        SysUser sysUser = this.baseService.getById(id);
        return this.update(SysUserUtil.create(sysUser).initSaltAndPassword());
    }

}
