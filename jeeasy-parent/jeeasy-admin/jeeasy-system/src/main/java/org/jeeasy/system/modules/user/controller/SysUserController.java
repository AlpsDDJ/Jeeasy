package org.jeeasy.system.modules.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.vo.R;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@RequestMapping("/sys/user")
@Slf4j
@Api(tags="系统用户")
public class SysUserController {

    @Resource
    private ISysUserService sysUserService;

    @GetMapping("/findUser")
    @ApiOperation(value="系统用户-查找用户", notes="系统用户-查找用户")
    public R<SysUser> findUser(@RequestParam("userName") String userName){
        return R.ok(sysUserService.queryByUserName(userName));
    }
}
