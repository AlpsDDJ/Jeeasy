package org.jeeasy.generate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.jeeasy.generate.service.GeneratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "代码生成")
@RequestMapping("/gen/generator")
public class GeneratorController {

    @Resource
    GeneratorService generatorService;

    @PostMapping
    @Operation(summary = "执行")
    public R<?> generator(@RequestBody GeneratorDto module) {
        log.info("module:{}", module);
        generatorService.generator(module);
        return R.ok();
    }
}
