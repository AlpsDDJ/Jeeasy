package org.jeeasy.common.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 常用工具类
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public class Tools {

    public static boolean isEmpty(Object obj) {
        return BeanUtil.isEmpty(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        return BeanUtil.isNotEmpty(obj);
    }

    public static String getTime() {
        return DateUtil.now();
    }

    public static String getProxyIp(HttpServletRequest request){
        return "";
    }

}
