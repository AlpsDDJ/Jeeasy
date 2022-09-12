package org.jeeasy.sso.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import com.ejlchina.okhttps.OkHttps;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.common.core.domain.model.AuthUserModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.sso.provider.AuthServiceProvider;
import org.jeeasy.sso.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2022-09-10 10:19
 */
@Slf4j
@RestController
public class SsoServerController {

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @RequestMapping("/sso/{handle}")
    @CrossOrigin("*")
    public Object ssoRequest(Map<String, Object> params, @PathVariable("handle") String handle) {
        log.info("SSO：[{}]，请求参数：[{}]", handle, params.toString());
        return SaSsoHandle.serverRequest();
    }

    /**
     * 配置SSO相关参数
     */
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置：未登录时返回的View
        sso.setNotLoginView(() -> {
            String msg = "当前会话在SSO-Server端尚未登录，请先访问"
                    + "<a href='/sso/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                    + "进行登录之后，刷新页面开始授权";
            return msg;
        });

        // 配置：登录处理函数
        // http://192.168.1.2:8888/sso/doLogin?name=13257805204&pwd=123456&type=fn_memberhttp://192.168.1.2:8888/sso/doLogin?name=13257805204&pwd=123456&type=fn_member
        sso.setDoLoginHandle((name, pwd) -> {
//            String username = SaHolder.getRequest().getParam("username");
//            String password = SaHolder.getRequest().getParam("password");
            // 此处仅做模拟登录，真实环境应该查询数据进行登录
            IAuthService<?> authService = authServiceProvider.getAuthService();
            IAuthUser authUser = authService.login(name, pwd);
            if(BeanUtil.isNotEmpty(authUser)) {
                StpUtil.login(authUser.id());
                authService.setSessionUser(authUser);
                authService.onAuthenticationSuccess(authUser);
                return R.ok("登录成功！").setData(StpUtil.getTokenValue());
            }
//            authService.
//            if("sa".equals(name) && "123456".equals(pwd)) {
//                StpUtil.login(10001);
//                return SaResult.ok("登录成功！").setData(StpUtil.getTokenValue());
//            }
            return R.error("登录失败！");
        });

        // 配置 Http 请求处理器 （在模式三的单点注销功能下用到，如不需要可以注释掉）
        sso.setSendHttp(url -> {
            try {
                // 发起 http 请求
                System.out.println("发起请求：" + url);
                return OkHttps.sync(url).get().getBody().toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}
