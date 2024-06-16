package org.jeeasy.sso.controller;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.service.IAuthService;
import org.jeeasy.common.core.tools.RequestUtil;
import org.jeeasy.sso.provider.AuthServiceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * SSO-Server端：处理所有SSO相关请求
     * http://{host}:{port}/sso/auth			-- 单点登录授权地址，接受参数：redirect=授权重定向地址
     * http://{host}:{port}/sso/doLogin		-- 账号密码登录接口，接受参数：name、pwd
     * http://{host}:{port}/sso/checkTicket	-- Ticket校验接口（isHttp=true时打开），接受参数：ticket=ticket码、ssoLogoutCall=单点注销回调地址 [可选]
     * http://{host}:{port}/sso/logout			-- 单点注销地址（isSlo=true时打开），接受参数：loginId=账号id、secretkey=接口调用秘钥
     */
    @RequestMapping("/sso/{handle}")
    @CrossOrigin("*")
    public Object ssoRequest(@PathVariable("handle") String handle) {
        log.info("SSO：[{}]", handle);
        //log.info("getPermissionList：[{}]", StpUtil.getPermissionList());

        return SaSsoProcessor.instance.serverDister();
    }

    @RequestMapping("/sso/userinfo")
    @CrossOrigin("*")
    public R<IAuthUser> userinfo() {
        return R.ok((IAuthUser) StpUtil.getSession().get(IAuthService.SESSION_USER_KEY));
    }

    /**
     * 配置SSO相关参数
     */
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置：未登录时返回的View
        sso.setNotLoginView(() -> "当前会话在SSO-Server端尚未登录，请先访问"
                + "<a href='/sso/doLogin?name=sa&pwd=123456' target='_blank'> doLogin登录 </a>"
                + "进行登录之后，刷新页面开始授权");


        // 配置：登录处理函数
        // http://192.168.1.2:8888/sso/doLogin?name=13257805204&pwd=123456&type=fn_memberhttp://192.168.1.2:8888/sso/doLogin?name=13257805204&pwd=123456&type=fn_member
        sso.setDoLoginHandle((name, pwd) -> {
//            String username = SaHolder.getRequest().getParam("username");
//            String password = SaHolder.getRequest().getParam("password");
            HttpServletRequest request = (HttpServletRequest) SaHolder.getRequest().getSource();
            Map<String, Object> params = RequestUtil.getParams(request);
            if (StringUtils.isBlank(name)) {
                name = params.get("name").toString();
            }
            if (StringUtils.isBlank(pwd)) {
                pwd = params.get("pwd").toString();
            }
            // 此处仅做模拟登录，真实环境应该查询数据进行登录
            IAuthService<?> authService = authServiceProvider.getAuthService();
            IAuthUser authUser = authService.login(name, pwd);
            if (BeanUtil.isNotEmpty(authUser)) {
                StpUtil.login(authUser.id());
//                authService.setSessionUser(authUser);
                authService.onAuthenticationSuccess(authUser);
                String userinfoUrl = sso.getUserinfoUrl();
                log.info(userinfoUrl);
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
        //sso.setSendHttp(url -> {
        //    try {
        //        // 发起 http 请求
        //        // System.out.println("发起请求：" + url);
        //        return OkHttp.sync(url).get().getBody().toString();
        //    } catch (Exception e) {
        //        e.printStackTrace();
        //        return null;
        //    }
        //});
    }
}
