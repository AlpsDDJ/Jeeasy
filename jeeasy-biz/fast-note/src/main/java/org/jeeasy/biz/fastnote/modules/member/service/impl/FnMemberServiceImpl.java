package org.jeeasy.biz.fastnote.modules.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.biz.fastnote.modules.member.api.dto.MemberRegisterDTO;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.biz.fastnote.modules.member.mapper.FnMemberMapper;
import org.jeeasy.biz.fastnote.modules.member.service.FnMemberService;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.handler.userpwd.UserPasswordHandler;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2022/9/11
 */
@Service
public class FnMemberServiceImpl extends ServiceImpl<FnMemberMapper, FnMember> implements FnMemberService {


    @Override
    public FnMember login(String phoone, String password) {
        FnMember check = findMemberByPhone(phoone);
        if(BeanUtil.isEmpty(check)){
            throw new JeeasyException("用户未注册");
        }
        UserPasswordHandler<FnMember> memberHandler = UserPasswordHandler.create(check);
        if(memberHandler.checkPassword(password)){
            return check;
        } else {
            throw new JeeasyException("密码错误");
        }
    }

    @Override
    public void register(MemberRegisterDTO memberRegisterDTO) {
        FnMember check = this.findMemberByPhone(memberRegisterDTO.getPhone());
        if(BeanUtil.isNotEmpty(check)){
            throw new JeeasyException("手机号已注册");
        }
        if(!"100866".equals(memberRegisterDTO.getCode())) {
            throw new JeeasyException("验证码错误或验证码已失效");
        }
        check = new FnMember();
        check.setPhone(memberRegisterDTO.getPhone());
        UserPasswordHandler<FnMember> memberHandler = UserPasswordHandler.create(check);
        memberHandler.initSaltAndPassword();
        check.insert();
    }

    @Override
    public FnMember findMemberByPhone(String phone) {
        return super.getOne(new LambdaQueryWrapper<FnMember>().eq(FnMember::getPhone, phone));
    }


}
