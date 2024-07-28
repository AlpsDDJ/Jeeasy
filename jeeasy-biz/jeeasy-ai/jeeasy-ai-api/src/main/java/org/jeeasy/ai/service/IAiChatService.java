package org.jeeasy.ai.service;

import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 *
 */

public interface IAiChatService {
    SseEmitter streamChat(ChatMessageDTO messageDTO);

    R<ChatResponseVO> chat(ChatMessageDTO messageDTO);
}
