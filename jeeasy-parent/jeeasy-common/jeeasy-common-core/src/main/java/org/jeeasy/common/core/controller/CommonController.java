package org.jeeasy.common.core.controller;

import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.CaptchaVo;
import org.jeeasy.common.core.vo.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Describe: 验证码控制器
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@RestController
public class CommonController {

    /**
     * 验证码生成
     */
    @GetMapping({"/noUser", "/403", "/401"})
    public R<?> noUser() throws Exception {
        return R.noUser("未登录");
    }

    /**
     * 验证码生成
     */
    @GetMapping("captcha/generate")
    public R<CaptchaVo> generate() throws Exception {
        return Tools.createCaptcha(Tools.createId());
    }

    /**
     * 异步验证
     *
     * @param id      验证码id
     * @param captcha 验证码
     * @return 验证结果
     */
    @RequestMapping("captcha/verify")
    public R<?> verify(String id, String captcha) {
        return Tools.verifyCaptcha(id, captcha);
    }
}