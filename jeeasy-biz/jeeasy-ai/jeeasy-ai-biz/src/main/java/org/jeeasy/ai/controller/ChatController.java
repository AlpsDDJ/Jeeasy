package org.jeeasy.ai.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.IAiChatService;
import org.jeeasy.ai.utils.ChatUtil;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ai/chat")
@RequiredArgsConstructor
public class ChatController {

    @Qualifier("AiChatService")
    private final IAiChatService aiChatService;
    private final OpenAiChatModel openAIClient;

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "流式对话", description = "流式对话")
    public SseEmitter test() {
        SseEmitter sseEmitter = aiChatService.streamChat(ChatMessageDTO.withMessage("你好！"));
        //return Mono.justOrEmpty(sseEmitter);
        //return "okk!";
        return sseEmitter;
    }

    @PostMapping(value = "/flux")
    @Operation(summary = "流式对话", description = "流式对话")
    public Flux<String> fluxChat(@RequestBody ChatMessageDTO messageDTO) {
        Prompt prompt = new Prompt(List.of(ChatUtil.toChatMessage(messageDTO)));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("发起请求");
        Flux<ChatResponse> response = openAIClient.stream(prompt);
        //Flux.from(response, (chatResponse, sink) -> {
        //    return chatResponse
        //});
        return response.map(chatResponse -> {
            try {
                // 获取当前响应的生成结果列表。
                List<Generation> generations = chatResponse.getResults();

                // 检查生成结果列表是否为空。
                if (!CollectionUtils.isEmpty(generations)) {
                    // 遍历每个生成结果。
                    for (Generation generation : generations) {
                        // 获取生成的元数据，用于判断是否应停止发送。
                        String finishReason = generation.getMetadata().getFinishReason();

                        // 如果生成结果未指示停止，则发送相关内容给客户端。
                        if (!StringUtils.equalsIgnoreCase(finishReason, "stop")) {
                            // 获取实际的助手消息。
                            AssistantMessage assistantMessage = generation.getOutput();
                            // 获取消息内容并检查是否为空。
                            String content = assistantMessage.getContent();
                            if (StringUtils.isNotEmpty(content)) {
                                log.debug("content: {}", content);
                                return content;
                            }
                        } else {
                            return "stop";
                        }
                    }
                }
            } catch (Exception e) {
                // 在处理过程中发生异常时，完成SSE发送并记录异常。
                //emitter.completeWithError(e);
                log.error(e.getLocalizedMessage());
            }
            return "";
        });
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
