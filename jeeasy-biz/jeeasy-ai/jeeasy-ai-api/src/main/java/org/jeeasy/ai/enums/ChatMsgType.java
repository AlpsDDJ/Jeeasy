package org.jeeasy.ai.enums;

public enum ChatMsgType {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system"),
    FUNCTION("function");

    private final String value;

    private ChatMsgType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ChatMsgType fromValue(String value) {
        ChatMsgType[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            ChatMsgType messageType = var1[var3];
            if (messageType.getValue().equals(value)) {
                return messageType;
            }
        }

        throw new IllegalArgumentException("Invalid MessageType value: " + value);
    }
}
