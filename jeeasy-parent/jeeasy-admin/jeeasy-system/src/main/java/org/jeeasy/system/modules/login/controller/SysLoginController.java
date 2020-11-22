package org.jeeasy.system.modules.login.controller;

import io.swagger.annotations.Api;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录接口文档
 * 只为生成文档
 * 登录操作由 spring security 完成
 * @author AlpsDDJ
 * @date 2020/11/11
 */
@RestController
@Api(tags = "系统用户登录")
public class SysLoginController {

    @Resource
    private SysUserService sysUserService;

//    @PostMapping("/login")
//    @ApiOperation(value = "登录", notes = "用户名密码登录")
//    public R<?> login(@ModelAttribute LoginUserModel loginUserModel){
////        if(Tools.verifyCaptcha(loginUserModel.getCaptchaKey(), loginUserModel.getCaptcha())){
////            SysUser sysUser = sysUserService.login(loginUserModel.getUsername(), loginUserModel.getPassword());
////            return R.ok();
////        } else {
////            return R.error("验证码错误");
////        }
//        return R.ok();
//    }

}
