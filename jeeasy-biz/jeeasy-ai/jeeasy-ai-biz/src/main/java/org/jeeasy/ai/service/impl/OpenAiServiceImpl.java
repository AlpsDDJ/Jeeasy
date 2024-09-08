package org.jeeasy.ai.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@Service("OpenAiService")
@Slf4j
@RequiredArgsConstructor
public class OpenAiServiceImpl extends AiServiceImpl {
    private final OpenAiChatModel openAIClient;

    @Override
    protected ChatModel chatModel() {
        return openAIClient;
    }
}
