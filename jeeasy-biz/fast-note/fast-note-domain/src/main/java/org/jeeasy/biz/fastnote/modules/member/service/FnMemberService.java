package org.jeeasy.biz.fastnote.modules.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.biz.fastnote.modules.member.dto.MemberRegisterDTO;

/**
 * @author AlpsDDJ
 * @date 2022/9/11 10:01
 */
public interface FnMemberService extends IService<FnMember> {

    FnMember login(String phoone, String password);

    void register(MemberRegisterDTO memberRegisterDTO);

    FnMember findMemberByPhone(String phone);

}
