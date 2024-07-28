package org.jeeasy.ai.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.IAiChatService;
import org.jeeasy.ai.utils.ChatUtil;
import org.jeeasy.ai.utils.SseUtil;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;

@Service("AiChatService")
@Slf4j
@RequiredArgsConstructor
public class AiChatServiceImpl implements IAiChatService {
    private final OpenAiChatModel openAIClient;

    @Override
    public SseEmitter streamChat(ChatMessageDTO messageDTO) {
        Prompt prompt = new Prompt(List.of(ChatUtil.toChatMessage(messageDTO)));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("发起请求");
        Flux<ChatResponse> response = openAIClient.stream(prompt);
        stopWatch.stop();
        stopWatch.start("创建sse");
        SseEmitter sseEmitter = SseUtil.send(response, messageDTO.getSessionId());
        stopWatch.stop();
        log.debug("{}", stopWatch.prettyPrint());
        return sseEmitter;
    }

    @Override
    public R<ChatResponseVO> chat(ChatMessageDTO messageDTO) {
        Prompt prompt = new Prompt(List.of(ChatUtil.toChatMessage(messageDTO)));
        ChatResponse response = openAIClient.call(prompt);
        List<ChatResponseVO.RespMessage> respMessages = response.getResults().stream().map(result -> {
            ChatResponseVO.RespMessage respMessage = new ChatResponseVO.RespMessage();
            respMessage.setContent(result.getOutput().getContent());
            respMessage.setType(result.getOutput().getMessageType().getValue());
            return respMessage;
        }).toList();
        String sessionId = StringUtils.isNotEmpty(messageDTO.getSessionId()) ? messageDTO.getSessionId() : ChatUtil.createSessionId();
        ChatResponseVO chatResponseVO = new ChatResponseVO(respMessages, sessionId);
        return R.ok(chatResponseVO);
    }
}
