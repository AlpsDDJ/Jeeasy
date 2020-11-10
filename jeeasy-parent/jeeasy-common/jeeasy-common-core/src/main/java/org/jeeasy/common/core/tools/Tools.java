package org.jeeasy.common.core.tools;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import org.jeeasy.common.core.vo.CaptchaVo;
import org.jeeasy.common.core.vo.R;

import javax.servlet.http.HttpServletRequest;

/**
 * 常用工具类
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public class Tools {

    private static final Long CAPTCHA_TIME = DateUnit.SECOND.getMillis() * 3 * 60;

    private static Cache<String, LineCaptcha> captchaCache = CacheUtil.newFIFOCache(10000);

    public static boolean isEmpty(Object obj) {
        return BeanUtil.isEmpty(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        return BeanUtil.isNotEmpty(obj);
    }

    public static String getTime() {
        return DateUtil.now();
    }

    public static String getProxyIp(HttpServletRequest request) {
        return "";
    }

    public static String createId() {
        return IdUtil.randomUUID();
    }

    public static R<CaptchaVo> createCaptcha(String id) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        captchaCache.put(id, lineCaptcha, CAPTCHA_TIME);
        CaptchaVo captchaVo = new CaptchaVo(id, lineCaptcha.getImageBase64());
        return R.ok(captchaVo);
    }

    public static R<?> verifyCaptcha(String id, String captcha) {
        LineCaptcha lineCaptcha = captchaCache.get(id);
        if(isEmpty(lineCaptcha)){
            return R.error("验证码失效");
        }
        if(!captchaCache.get(id).verify(captcha)){
            return R.error("验证码错误");
        }
        return R.ok("验证通过");
    }

}
