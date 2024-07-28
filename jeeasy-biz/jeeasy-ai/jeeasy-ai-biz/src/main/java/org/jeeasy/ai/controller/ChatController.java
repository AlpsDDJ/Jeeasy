package org.jeeasy.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.utils.ChatUtil;
import org.jeeasy.ai.utils.SseUtil;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class ChatController {

    private final OpenAiChatModel openAIClient;

    @GetMapping(value = "/stream")
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter streamChat(@RequestBody ChatMessageDTO messageDTO) {
        Prompt prompt = new Prompt(List.of(messageDTO.toChatMessage()));
        return SseUtil.send(openAIClient.stream(prompt), messageDTO.getSessionId());
    }

    @GetMapping
    @Operation(summary = "普通对话", description = "普通对话")
    public Mono<R<ChatResponseVO>> chat(@RequestBody ChatMessageDTO messageDTO) {
        Prompt prompt = new Prompt(List.of(messageDTO.toChatMessage()));
        ChatResponse response = openAIClient.call(prompt);
        List<ChatResponseVO.RespMessage> respMessages = response.getResults().stream().map(result -> {
            ChatResponseVO.RespMessage respMessage = new ChatResponseVO.RespMessage();
            respMessage.setContent(result.getOutput().getContent());
            respMessage.setType(result.getOutput().getMessageType());
            return respMessage;
        }).toList();
        String sessionId = StringUtils.isNotEmpty(messageDTO.getSessionId()) ? messageDTO.getSessionId() : ChatUtil.createSessionId();
        ChatResponseVO chatResponseVO = new ChatResponseVO(respMessages, sessionId);
        return Mono.justOrEmpty(R.ok(chatResponseVO));
    }

    @GetMapping(value = "/test")
    public SseEmitter test(@RequestParam String message) {
        String systemPrompt = "{prompt}";
        SystemPromptTemplate systemPromptTemplate = new SystemPromptTemplate(systemPrompt);

        Message userMessage = new UserMessage(message);

        Message systemMessage = systemPromptTemplate.createMessage(Map.of("prompt", "你是一个有用的人工智能助手"));
        Prompt prompt = new Prompt(List.of(userMessage, systemMessage));

        Flux<ChatResponse> responseFlux = openAIClient.stream(prompt);
        return SseUtil.send(responseFlux, null);
    }
}
