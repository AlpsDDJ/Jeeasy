package org.jeeasy.ai.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SseMessage {
    private String content;
    private Type type;
    private String sessionId;

    public enum Type {
        RESULT,
        COMPLETE,
        ERROR;
    }
}
