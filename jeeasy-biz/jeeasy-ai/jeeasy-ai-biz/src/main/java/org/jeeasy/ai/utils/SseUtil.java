package org.jeeasy.ai.utils;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.common.core.tools.HttpUtil;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Log4j2
public class SseUtil {

    public static void main(String[] args) {
        String ss = "11111\n1111;11111\n1111\n11111\n";
        System.out.println(ss.replaceAll("\n", "\\\\n"));
    }


    /**
     * 通过SSE向客户端发送聊天响应。
     *
     * @param response 聊天响应的流，包含生成的结果。
     * @return SseEmitter实例，用于与客户端进行SSE通信。
     */
    public static final SseEmitter send(Flux<ChatResponse> response, String sessionId) {
        if (StringUtils.isEmpty(sessionId)) {
            sessionId = ChatUtil.createSessionId();
        }
        HttpServletResponse httpResponse = HttpUtil.getResponse();
        httpResponse.setContentType("text/event-stream");
        httpResponse.setCharacterEncoding("UTF-8");
        // 创建一个新的SseEmitter实例，用于与客户端建立SSE连接。
        SseEmitter emitter = new SseEmitter();

        // 订阅聊天响应流，每当有新的响应时，执行相应的处理。
        final String finalSessionId = sessionId;

        AtomicInteger index = new AtomicInteger();
        response.subscribe(x -> {
            try {
                // 获取当前响应的生成结果列表。
                List<Generation> generations = x.getResults();

                // 检查生成结果列表是否为空。
                if (!CollectionUtils.isEmpty(generations)) {
                    // 遍历每个生成结果。
                    for (Generation generation : generations) {
                        // 获取生成的元数据，用于判断是否应停止发送。
                        String finishReason = generation.getMetadata().getFinishReason();

                        // 如果生成结果未指示停止，则发送相关内容给客户端。
                        if (!StringUtils.equalsIgnoreCase(finishReason, "stop")) {
                            // 获取实际的助手消息。
                            AssistantMessage assistantMessage = generation.getOutput();
                            // 获取消息内容并检查是否为空。
                            String content = assistantMessage.getContent();
                            if (StringUtils.isNotEmpty(content)) {
                                // 发送消息内容给客户端。
                                SseEmitter.SseEventBuilder builder = SseEmitter.event();
                                //content.replaceAll("\n", "\\n");
                                emitter.send(SseEmitter.event()
                                        .id(String.valueOf(index.getAndIncrement()))
                                        .name("result")
                                        .reconnectTime(System.currentTimeMillis())
                                        .data(content.replaceAll("\n", "\\\n")));
                                //for (String line : content.split("\n")) {
                                //
                                //    //Set<ResponseBodyEmitter.DataWithMediaType> events = SseEmitter.event()
                                //    //        .id(String.valueOf(index.getAndIncrement()))
                                //    //        .name("result")
                                //    //        .reconnectTime(System.currentTimeMillis())
                                //    //        .data(line)
                                //    //        .build();
                                //    ////new ResponseBodyEmitter.DataWithMediaType(line);
                                //    ////events.add()
                                //    emitter.send(SseEmitter.event()
                                //            .id(String.valueOf(index.getAndIncrement()))
                                //            .name("result")
                                //            .reconnectTime(System.currentTimeMillis())
                                //            .data(line));
                                //}
                            }
                        } else {
                            // 如果生成结果指示停止，完成SSE发送。
                            //Set<ResponseBodyEmitter.DataWithMediaType> events = SseEmitter.event()
                            //        .id(String.valueOf(index.getAndIncrement()))
                            //        .name("complete")
                            //        .reconnectTime(System.currentTimeMillis())
                            //        .data(finalSessionId)
                            //        .build();
                            emitter.send(SseEmitter.event()
                                    .id(String.valueOf(index.getAndIncrement()))
                                    .name("complete")
                                    .reconnectTime(System.currentTimeMillis())
                                    .data(finalSessionId));
                            emitter.complete();
                        }
                    }
                }
            } catch (Exception e) {
                // 在处理过程中发生异常时，完成SSE发送并记录异常。
                emitter.completeWithError(e);
                log.error(e.getLocalizedMessage());
            }
        });
        //new Thread(() -> {
        //
        //}).start();


        // 返回SseEmitter实例，供客户端使用。
        return emitter;
    }
}
