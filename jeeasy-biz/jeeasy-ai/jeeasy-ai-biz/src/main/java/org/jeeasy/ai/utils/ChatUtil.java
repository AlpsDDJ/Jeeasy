package org.jeeasy.ai.utils;

import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.enums.ChatMsgType;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

public class ChatUtil {
    public static String createSessionId() {
        return String.valueOf(System.currentTimeMillis());
    }


    public static Message toChatMessage(ChatMessageDTO messageDTO) {
        ChatMsgType type = ChatMsgType.fromValue(messageDTO.getMessageType());
        switch (type) {
            case SYSTEM:
                return new SystemMessage(messageDTO.getContent());
            default:
                return new UserMessage(messageDTO.getContent());
        }
    }
}
