package org.jeeasy.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.ai.domain.AiChatApp;

/**
 * AI 应用 Service
 *
 * @author wei.yang
 * @date 2024-07-29 13:04:49
 */
public interface AiChatAppService extends IService<AiChatApp> {
    AiChatApp getByCode(String code);
}