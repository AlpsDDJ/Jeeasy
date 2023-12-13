package org.jeeasy.biz.fastnote.api.member;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.biz.fastnote.modules.member.dto.MemberLoginDTO;
import org.jeeasy.biz.fastnote.modules.member.dto.MemberRegisterDTO;
import org.jeeasy.biz.fastnote.modules.member.service.FnMemberService;
import org.jeeasy.common.core.annotation.controller.ApiController;
import org.jeeasy.common.core.base.SimpleBaseApi;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.IAuthService;
import org.jeeasy.common.core.service.IDictTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

/**
 * FastNote用户控制器
 *
 * @author AlpsDDJ
 * @description FastNote用户
 * @date 2022/9/11 13:52:05
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "FastNote用户")
@ApiController("/fn/member")
public class FnMemberApi extends SimpleBaseApi<FnMemberService, FnMember> {

    @Lazy
    @Autowired
    private IDictTranslationService dictTranslationService;

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping("register")
    @Operation(summary = "注册用户", description = "注册用户")
    public R<?> register(@RequestBody MemberRegisterDTO entity) {
        super.service.register(entity);
        return R.ok().setData("注册成功");
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @GetMapping("login")
    @Operation(summary = "用户登录", description = "用户登录")
    public void login(MemberLoginDTO entity, HttpServletResponse response) throws IOException {
        String ssoUrl = StrUtil.format("/sso/doLogin?name={}&pwd={}&type=fn_member", entity.getPhone(), entity.getPassword());
        response.sendRedirect(ssoUrl);
    }

    /**
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @GetMapping("current")
//    @SaCheckRole(LoginMember.VIRTUAL_MEMBER_ROLE)
    @Operation(summary = "获取当前登录用户", description = "获取当前登录用户")
    public R<?> current() {
        return R.ok(StpUtil.getSession().get(IAuthService.SESSION_USER_KEY));
    }

    /**
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @GetMapping("test")
//    @SaCheckRole(LoginMember.VIRTUAL_MEMBER_ROLE)
    @Operation(summary = "测试", description = "测试")
    public R<TableDictVo> test() {
        TableDictVo data = dictTranslationService.getTableDictByCode("sys_role").getData();
        return R.ok(data);
    }

}