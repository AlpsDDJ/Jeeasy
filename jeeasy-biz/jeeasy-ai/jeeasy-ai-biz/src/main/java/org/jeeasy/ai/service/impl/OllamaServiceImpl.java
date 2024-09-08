package org.jeeasy.ai.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.ai.domain.AiChatApp;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.AiChatAppService;
import org.jeeasy.ai.service.IAiChatService;
import org.jeeasy.ai.utils.ChatUtil;
import org.jeeasy.ai.utils.SseUtil;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.ai.vo.SseMessage;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service("OllamaService")
@Slf4j
@RequiredArgsConstructor
public class OllamaServiceImpl implements IAiChatService {

    private final OllamaChatModel ollamaChatModel;
    private final AiChatAppService chatAppService;


    protected Prompt createPrompt(ChatMessageDTO messageDTO) {
        List<Message> messageList = CollectionUtil.toList(ChatUtil.toChatMessage(messageDTO));
        String appCode = messageDTO.getAppCode();
        String model = messageDTO.getModel();
        OllamaOptions optionBuilder = OllamaOptions.create();
        if (Tools.isNotEmpty(appCode)) {
            AiChatApp app = chatAppService.getByCode(appCode);
            if (Tools.isEmpty(app)) {
                throw new JeeasyException(String.format("应用编号[%s]不存在", appCode));
            }
            Map<String, Object> params = messageDTO.getParams();
            String prompt = Tools.replaceParams(app.getPrompt(), params);
            Message sysMessage = new SystemMessage(prompt);
            messageList.add(sysMessage);
            optionBuilder.withModel(Tools.isNotEmpty(model) ? model : app.getModel());
        } else {
            messageList.add(new SystemMessage("你是一个智能助手"));
        }
        return new Prompt(messageList, optionBuilder);
    }


    @Override
    public SseEmitter streamChat(ChatMessageDTO messageDTO) {
        Prompt prompt = createPrompt(messageDTO);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("发起请求");
        Flux<ChatResponse> response = ollamaChatModel.stream(prompt);
        stopWatch.stop();
        stopWatch.start("创建sse");
        SseEmitter sseEmitter = SseUtil.send(response, messageDTO.getSessionId());
        stopWatch.stop();
        log.debug("{}", stopWatch.prettyPrint());
        return sseEmitter;
    }

    @Override
    public Flux<SseMessage> fluxChat(ChatMessageDTO messageDTO) {
        Prompt prompt = createPrompt(messageDTO);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("发起请求");
        Flux<ChatResponse> response = ollamaChatModel.stream(prompt);
        String sessionId = messageDTO.getSessionId();
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = ChatUtil.createSessionId();
        }
        String finalSessionId = sessionId;
        return response.mapNotNull(resp -> {
            for (Generation result : resp.getResults()) {
                String finishReason = result.getMetadata().getFinishReason();
                if (StringUtils.equalsAnyIgnoreCase(finishReason, "stop", "unknown")) {
                    return SseMessage.builder().type(SseMessage.Type.COMPLETE).sessionId(finalSessionId).build();
                }
                String content = result.getOutput().getContent();
                return SseMessage.builder().type(SseMessage.Type.RESULT).content(content).build();
            }
            return null;
        });
    }

    @Override
    public R<ChatResponseVO> chat(ChatMessageDTO messageDTO) {
        Prompt prompt = createPrompt(messageDTO);
        ChatResponse response = ollamaChatModel.call(prompt);
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
