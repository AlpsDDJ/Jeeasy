package org.jeeasy.ai.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.jeeasy.ai.domain.AiChatApp;
import org.jeeasy.ai.service.AiChatAppService;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleCurdController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.*;


/**
 * AI 应用 Controller
 *
 * @author wei.yang
 * @date 2024-07-29 13:04:49
 */
@RestController
@Tag(name = "AI 应用")
@RequestMapping("/ai/chat/app")
public class AiChatAppController extends SimpleCurdController<AiChatAppService, AiChatApp> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "AI 应用列表", description = "AI 应用列表")
    public R<IPage<AiChatApp>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找AI 应用", description = "根据ID查找AI 应用")
    public R<AiChatApp> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑AI 应用", description = "编辑AI 应用")
    public R<?> edit(@RequestBody AiChatApp entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加AI 应用", description = "添加AI 应用")
    public R<?> add(@RequestBody AiChatApp entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除AI 应用", description = "根据ID删除AI 应用")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除AI 应用", description = "批量删除AI 应用")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

}
