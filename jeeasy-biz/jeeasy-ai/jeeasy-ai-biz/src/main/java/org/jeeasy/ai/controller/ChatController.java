package org.jeeasy.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.IAiChatService;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class ChatController {

    @Qualifier("AiChatService")
    private final IAiChatService aiChatService;

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter test() {
        SseEmitter sseEmitter = aiChatService.streamChat(ChatMessageDTO.withMessage("你好！"));
        //return Mono.justOrEmpty(sseEmitter);
        //return "okk!";
        return sseEmitter;
    }

    @PostMapping(value = "/stream")
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter streamChat(@RequestBody ChatMessageDTO messageDTO) {
        SseEmitter sseEmitter = aiChatService.streamChat(messageDTO);
        //return Mono.justOrEmpty(sseEmitter);
        return sseEmitter;
    }

    @PostMapping
    @Operation(summary = "普通对话", description = "普通对话")
    public Mono<R<ChatResponseVO>> chat(@RequestBody ChatMessageDTO messageDTO) {
        return Mono.justOrEmpty(aiChatService.chat(messageDTO));
    }
}
