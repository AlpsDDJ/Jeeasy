package org.jeeasy.ai.service;

import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.api.fallback.CommonFallback;
import org.jeeasy.common.core.config.constant.ServiceNameConstant;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@FeignClient(path = "ai/chat", contextId = "IAiChatApiFeign", value = ServiceNameConstant.SERVICE_AI, fallbackFactory = IAiChatApiFeign.FallbackFactory.class/*, configuration = FeignConfig.class*/)
@ConditionalOnMissingClass("org.jeeasy.ai.service.impl.AiChatServiceImpl")
public interface IAiChatApiFeign {
    @Component
    class FallbackFactory implements CommonFallback<IAiChatApiFeign> {
    }

    @PostMapping(value = "flux")
    feign.Response fluxChat(@RequestBody ChatMessageDTO messageDTO);

    @PostMapping(value = "stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    feign.Response streamChat(@RequestBody ChatMessageDTO messageDTO);

    @PostMapping
    R<ChatResponseVO> chat(@RequestBody ChatMessageDTO messageDTO);
}
