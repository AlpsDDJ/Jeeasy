package org.jeeasy.common.core.tools;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.domain.vo.CaptchaVo;
import org.jeeasy.common.core.domain.vo.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 常用工具类
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Slf4j
public class Tools {

    private static final Long CAPTCHA_TIME = DateUnit.SECOND.getMillis() * 3 * 60;

    private static Cache<String, LineCaptcha> captchaCache = CacheUtil.newFIFOCache(10000);

    public static boolean isEmpty(Object obj) {
        return !isNotEmpty(obj);
    }

    public static boolean isNotEmpty(Object obj) {
        if(obj instanceof String){
            return StrUtil.isNotEmpty((String)obj);
        } else if(obj instanceof Collection){
            return CollectionUtil.isNotEmpty((Collection)obj);
        }
        return BeanUtil.isNotEmpty(obj);
    }

    public static String getTime() {
        return DateUtil.now();
    }

    public static String getProxyIp() {
        return getProxyIp(ServletUtil.getRequest());
    }

    public static String getProxyIp(HttpServletRequest request) {
        return "";
    }

    public static String uuid() {
        return IdUtil.simpleUUID();
    }

    public static R<CaptchaVo> createCaptcha(String key) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        log.info("获取图片验证码: key = {}, code = {}", key, lineCaptcha.getCode());
        captchaCache.put(key, lineCaptcha, CAPTCHA_TIME);
        CaptchaVo captchaVo = new CaptchaVo(key, lineCaptcha.getImageBase64());
        return R.ok(captchaVo);
    }

    public static boolean verifyCaptcha(String key, String captcha) throws JeeasyException {
        LineCaptcha lineCaptcha = captchaCache.get(key);
        if(isEmpty(lineCaptcha)){
            throw new JeeasyException("验证码失效");
        }
        if(!captchaCache.get(key).verify(captcha)){
            throw new JeeasyException("验证码错误");
        }
        return true;
    }

}
