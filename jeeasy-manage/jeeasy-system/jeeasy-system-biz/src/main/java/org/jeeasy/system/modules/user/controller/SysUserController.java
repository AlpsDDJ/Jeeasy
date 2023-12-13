package org.jeeasy.system.modules.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.enums.DelFlagEnum;
import org.jeeasy.common.core.handler.userpwd.UserPasswordHandler;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.premission.domain.vo.MenuVo;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.model.ChangePasswordByOldPasswordModel;
import org.jeeasy.system.modules.user.domain.model.SysUserQueryPageModel;
import org.jeeasy.system.modules.user.domain.model.UserInfoModel;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Slf4j
@RestController
@Tag(name = "系统用户")
@RequestMapping("/sys/user")
public class SysUserController extends SimpleBaseController<SysUserService, SysUser> {
    @Autowired
    private SysPermissionService permissionService;

    @GetMapping
    @DictTranslation
    @Operation(summary = "用户列表", description = "用户列表")
    public R<IPage<SysUser>> list(SysUserQueryPageModel queryPageModel, HttpServletRequest req) {
        QueryWrapper<SysUser> wrapper = QueryGenerator.createWrapper(SysUser.class, req.getParameterMap());
        if (!queryPageModel.hasSort()) {
            wrapper.lambda().orderByAsc(SysUser::getUserNo);
        }
        String[] deptId = queryPageModel.getDepts();
        String[] roleId = queryPageModel.getRoles();
        if (Tools.isNotEmpty(deptId)) {
            List<String> deptIds = CollectionUtil.newArrayList(deptId);
            if (deptIds.size() == 1) {
                wrapper.eq("dept_id", deptIds.get(0));
            } else if (deptIds.size() > 1) {
                wrapper.in("dept_id", deptIds);
            }
        }
        if (Tools.isNotEmpty(roleId)) {
            List<String> roleIds = CollectionUtil.newArrayList(roleId);
            if (roleIds.size() == 1) {
                wrapper.eq("role_id", roleIds.get(0));
            } else if (roleIds.size() > 1) {
                wrapper.in("role_id", roleIds);
            }
        }
        wrapper.lambda().eq(SysUser::getDelFlag, DelFlagEnum.NO.getValue());
        IPage<SysUser> sysUserVoList = service.querySysUserVoPage(wrapper, queryPageModel);
        return R.ok(sysUserVoList);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找用户", description = "根据ID查找用户")
    public R<SysUser> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PostMapping
    @Operation(summary = "添加用户", description = "添加用户")
    public R<?> add(@RequestBody UserInfoModel model) {
        service.insertUserWithUserInfoModel(model);
        return R.ok().setMessage("添加成功");
    }

    @PutMapping
    @Operation(summary = "修改用户", description = "修改用户")
    public R<?> edit(@RequestBody UserInfoModel model) {
        service.updateUserWithUserInfoModel(model);
        return R.ok().setMessage("修改成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除用户", description = "根据ID删除用户")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除用户", description = "批量删除用户")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

    /**
     * 通过旧密码验证修改新密码
     *
     * @param model
     * @return
     */
    @PutMapping("/changePasswordByOldPassword")
    @Operation(summary = "修改用户密码", description = "通过旧密码验证修改新密码")
    public R<?> changePasswordByOldPassword(@RequestBody ChangePasswordByOldPasswordModel model) {
        SysUser sysUser = this.service.getById(model.getId());
        UserPasswordHandler userPasswordHandler = UserPasswordHandler.create(sysUser);
        if (userPasswordHandler.checkPassword(model.getOldPassword())) {
            userPasswordHandler.changePassword(model.getNewPassword());
            this.service.updateById(sysUser);
            return R.ok("密码修改成功");
        } else {
            return R.error("密码错误");
        }
    }

    /**
     * 重置用户密码为初始密码 [123456]
     *
     * @param id
     * @return {@link R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 21:50
     */
    @PutMapping("/resetPassword")
    @Operation(summary = "重置用户密码", description = "重置用户密码为初始密码")
    public R<?> resetPassword(@RequestBody String id) {
        SysUser sysUser = this.service.getById(id);
        return this.update(UserPasswordHandler.create(sysUser).initSaltAndPassword());
    }


    @GetMapping("/menus")
    @DictTranslation
    @Operation(summary = "当前登录用户菜单列表", description = "当前登录用户菜单列表")
    public R<List<MenuVo>> menus() {
        String currentAuthUserId = StpUtil.getLoginId().toString();
        List<MenuVo> sysPermissions = permissionService.queryMenuByUserId(currentAuthUserId);
        return R.ok(sysPermissions);
    }

}
