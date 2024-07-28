package org.jeeasy.ai.dto;

import lombok.Data;
import org.jeeasy.ai.enums.ChatMsgType;


@Data
public class ChatMessageDTO {
    private String messageType = ChatMsgType.USER.getValue();
    private String content;
    private String sessionId;
    private Integer maxHistory = 0;

    public static ChatMessageDTO withMessage(String message) {
        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setContent(message);
        return messageDTO;
    }
}
