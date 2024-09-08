package org.jeeasy.ai.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.ai.enums.ChatMsgType;

import java.util.Map;


@Data
@Accessors(chain = true)
public class ChatMessageDTO {
    private String messageType = ChatMsgType.USER.getValue();
    private String content;
    private String sessionId;
    private String appCode;
    private Integer maxHistory = 0;
    private String model;

    private Map<String, Object> params;

    public static ChatMessageDTO withMessage(String message) {
        ChatMessageDTO messageDTO = new ChatMessageDTO();
        messageDTO.setContent(message);
        return messageDTO;
    }
}
