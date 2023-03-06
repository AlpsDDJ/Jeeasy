package org.jeeasy.common.core.tools;

import cn.hutool.json.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2023-03-05 18:03
 */
@Log4j2
public class RequestUtil {
    public static Map<String, Object> getParams(HttpServletRequest request) {
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
                // return jsonObject.get(key);
                // jsonObject.
                // params = JSONObject.parseObject(wholeStr.toString(), Map.class);
            }
        } catch (IOException e1) {
            log.error("" + e1);
        }
        return params;
    }
}
