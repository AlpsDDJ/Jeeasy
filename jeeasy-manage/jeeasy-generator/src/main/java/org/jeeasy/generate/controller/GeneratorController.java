package org.jeeasy.generate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.ai.dto.ChatMessageDTO;
import org.jeeasy.ai.service.IAiChatApiFeign;
import org.jeeasy.ai.vo.ChatResponseVO;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.jeeasy.generate.service.GeneratorService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@RestController
@Tag(name = "代码生成")
@RequestMapping("/gen/generator")
@RequiredArgsConstructor
public class GeneratorController {

    final GeneratorService generatorService;
    @Lazy
    @Resource
    IAiChatApiFeign aiChatService;

    @PostMapping
    @Operation(summary = "执行")
    public R<?> generator(@RequestBody GeneratorDto module) {
        log.info("module:{}", module);
        generatorService.generator(module);
        return R.ok();
    }

    @GetMapping(value = "aiStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "aiStream")
    public void aiStream(@RequestParam(required = false, defaultValue = "你好，你是谁？") String content, HttpServletResponse httpResponse) {
        httpResponse.setContentType("text/event-stream");
        httpResponse.setCharacterEncoding("UTF-8");
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setContent(content);
        feign.Response response = aiChatService.streamChat(dto);
        try (InputStream fileInputStream = response.body().asInputStream()) {
            ServletOutputStream outputStream = httpResponse.getOutputStream();
            fileInputStream.transferTo(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "aiFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "aiFlux")
    public void aiFlux(@RequestParam(required = false, defaultValue = "你好，你是谁？") String content, HttpServletResponse httpResponse) {
        httpResponse.setContentType("text/event-stream");
        httpResponse.setCharacterEncoding("UTF-8");
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setContent(content);
        feign.Response stringFlux = aiChatService.fluxChat(dto);
        try (InputStream inputStream = stringFlux.body().asInputStream()) {
            inputStream.transferTo(httpResponse.getOutputStream());
        } catch (IOException e) {

        }
        //return stringFlux;

        //try (InputStream fileInputStream = response.body().asInputStream()) {
        //    ServletOutputStream outputStream = httpResponse.getOutputStream();
        //    fileInputStream.transferTo(outputStream);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    @GetMapping("aiTest")
    @Operation(summary = "执行")
    public R<ChatResponseVO> aiTest(@RequestBody ChatMessageDTO module) {
        return aiChatService.chat(module);
    }
}
