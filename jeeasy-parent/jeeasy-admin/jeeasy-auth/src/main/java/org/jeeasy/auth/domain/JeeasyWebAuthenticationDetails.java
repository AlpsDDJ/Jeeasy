//package org.jeeasy.auth.domain;
//
//import cn.hutool.core.map.MapUtil;
//import cn.hutool.extra.servlet.ServletUtil;
//import lombok.Getter;
//import org.jeeasy.common.core.tools.Tools;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Map;
//
///**
// * @author AlpsDDJ
// * @version v1.0
// * @description JeeasyWebAuthenticationDetails
// * @date 2020-11-14
// */
//public class JeeasyWebAuthenticationDetails extends WebAuthenticationDetails {
//
//    public static final String AUTH_METHOD_KEY = "authMethod";
//    public static final String AUTH_REMEMBER_ME_KEY = "rememberMe";
//
//    @Getter
//    private String authMethod;
//
//    private Map<String, String> params;
//
//    public JeeasyWebAuthenticationDetails(HttpServletRequest request) {
//        super(request);
//        this.authMethod = request.getParameter(AUTH_METHOD_KEY);
//        Map<String, String> map = ServletUtil.getParamMap(request);
//        this.params = map;
//    }
//
//    public Object get(String key){
//        if(Tools.isEmpty(params)){
//            return null;
//        }
//        return params.get(key);
//    }
//
//    public Boolean getRememberMe(){
//        Boolean rememberMe = MapUtil.getBool(params, AUTH_REMEMBER_ME_KEY);
//        if(Tools.isEmpty(rememberMe)){
//            rememberMe = false;
//        }
//        return rememberMe;
//    }
//}
