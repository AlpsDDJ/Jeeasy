package org.jeeasy.common.core.tools;

import cn.hutool.json.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 *
 * @author wei.yang
 * @date 2023-03-05 18:03
 */
@Log4j2
public class HttpUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取请求参数
     *
     * @param request HTTP请求对象
     * @return 请求参数的Map对象
     */
    public static Map<String, Object> getReqParams(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        BufferedReader br;
        try {
            br = request.getReader();
            String str;
            StringBuilder wholeStr = new StringBuilder();
            while ((str = br.readLine()) != null) {
                wholeStr.append(str);
            }
            if (StringUtils.isNotEmpty(wholeStr.toString())) {
                JSONObject jsonObject = new JSONObject(wholeStr.toString());
                params = jsonObject.toBean(Map.class);
            }
        } catch (IOException e1) {
            log.error("" + e1);
        }
        return params;
    }

    /**
     * 获取请求参数
     *
     * @return 请求参数的Map对象
     */
    public static Map<String, Object> getReqParams() {
        return getReqParams(getRequest());
    }
}
