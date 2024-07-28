package org.jeeasy.ai.dto;

import lombok.Data;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;


@Data
public class ChatMessageDTO {
    private String messageType = MessageType.USER.getValue();
    private String content;

    private String sessionId;

    private Integer maxHistory = 0;

    public Message toChatMessage() {
        MessageType type = MessageType.fromValue(this.messageType);
        switch (type) {
            case SYSTEM:
                return new SystemMessage(content);
            default:
                return new UserMessage(content);
        }
    }
}
