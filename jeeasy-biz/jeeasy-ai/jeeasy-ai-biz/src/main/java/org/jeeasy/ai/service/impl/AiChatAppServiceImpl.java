package org.jeeasy.ai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.ai.domain.AiChatApp;
import org.jeeasy.ai.mapper.AiChatAppMapper;
import org.jeeasy.ai.service.AiChatAppService;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.springframework.stereotype.Service;

/**
 * AI 应用 ServiceImpl
 *
 * @author wei.yang
 * @date 2024-07-29 13:04:49
 */
@Service
public class AiChatAppServiceImpl extends ServiceImpl<AiChatAppMapper, AiChatApp> implements AiChatAppService {

    @Override
    public AiChatApp getByCode(String code) {
        LambdaQueryWrapper<AiChatApp> wrapper = QueryGenerator.ofLambdaWrapper();
        wrapper.eq(AiChatApp::getCode, code);
        return baseMapper.selectOne(wrapper);
    }
}
