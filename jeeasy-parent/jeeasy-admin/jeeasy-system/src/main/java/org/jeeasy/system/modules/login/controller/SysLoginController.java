package org.jeeasy.system.modules.login.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.system.modules.login.model.LoginUserModel;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AlpsDDJ
 * @date 2020/11/11
 */
@RestController
@Api(tags = "系统用户登录")
public class SysLoginController {

    @Resource
    private ISysUserService sysUserService;

    @PostMapping("/sys/login")
    @ApiOperation(value = "登录", notes = "用户名密码登录")
    public R<?> login(@RequestBody LoginUserModel loginUserModel){
        if(Tools.verifyCaptcha(loginUserModel.getCaptchaKey(), loginUserModel.getCaptcha())){
            SysUser sysUser = sysUserService.login(loginUserModel.getUsername(), loginUserModel.getPassword());
            return R.ok();
        } else {
            return R.error("验证码错误.");
        }
    }

}
