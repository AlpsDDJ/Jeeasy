package org.jeeasy.ai.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatResponseVO {
    private List<RespMessage> messages;
    private String sessionId;


    @Data
    public static class RespMessage {
        private String content;
        private MessageType type;
    }
}
