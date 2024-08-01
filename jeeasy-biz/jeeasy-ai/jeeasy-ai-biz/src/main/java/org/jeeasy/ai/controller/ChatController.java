package org.jeeasy.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.IAiChatService;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.ai.vo.SseMessage;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class ChatController {

    @Qualifier("AiChatService")
    private final IAiChatService aiChatService;
    //private final OpenAiChatModel openAIClient;
    //private final AiChatAppService chatAppService;

    @PostMapping
    @Operation(summary = "普通对话", description = "普通对话")
    public Mono<R<ChatResponseVO>> chat(@RequestBody ChatMessageDTO messageDTO) {
        return Mono.justOrEmpty(aiChatService.chat(messageDTO));
    }

    @PostMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式对话", description = "流式对话")
    public Flux<SseMessage> fluxChat(@RequestBody ChatMessageDTO messageDTO) {
        return aiChatService.fluxChat(messageDTO);
    }

    // 暂时无用
    @Deprecated
    @PostMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "sse流式对话", description = "sse流式对话")
    public SseEmitter chatSse(@RequestBody ChatMessageDTO messageDTO) {
        return aiChatService.streamChat(messageDTO);
    }

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter test() {
        return aiChatService.streamChat(ChatMessageDTO.withMessage("你好！"));
    }

    @PostMapping(value = "/stream")
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter streamChat(@RequestBody ChatMessageDTO messageDTO) {
        SseEmitter sseEmitter = aiChatService.streamChat(messageDTO);
        //return Mono.justOrEmpty(sseEmitter);
        return sseEmitter;
    }
}
