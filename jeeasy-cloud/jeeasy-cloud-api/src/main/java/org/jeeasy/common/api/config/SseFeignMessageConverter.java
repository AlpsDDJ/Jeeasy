package org.jeeasy.common.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Configuration
public class SseFeignMessageConverter {
    @Bean
    public HttpMessageConverter<SseEmitter> sseEmitterMessageConverter() {
        return new AbstractHttpMessageConverter<SseEmitter>(new MediaType("text", "event-stream")) {

            @Override
            protected boolean supports(Class<?> clazz) {
                return SseEmitter.class.equals(clazz);
            }

            @Override
            protected SseEmitter readInternal(Class<? extends SseEmitter> clazz, HttpInputMessage inputMessage)
                    throws IOException, HttpMessageNotReadableException {
                // 由于SseEmitter是无法被序列化的，因此这里我们直接返回一个新的SseEmitter实例
                return new SseEmitter();
            }

            @Override
            protected void writeInternal(SseEmitter t, HttpOutputMessage outputMessage)
                    throws IOException, HttpMessageNotWritableException {
                // 不需要实现，因为我们并不需要将SseEmitter写入响应
            }
        };
    }
}
