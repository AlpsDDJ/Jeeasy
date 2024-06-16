package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.jeeasy.generate.domain.GenTemplate;
import org.jeeasy.generate.service.GenTemplateService;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.*;


/**
* 代码生成模板 Controller
*
* @author wei.yang
* @date 2023-12-17 14:56:27
*/
@RestController
@Tag(name = "代码生成模板")
@RequestMapping("/gen/template")
public class GenTemplateController extends SimpleBaseController<GenTemplateService, GenTemplate> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "代码生成模板列表", description = "代码生成模板列表")
    public R<IPage<GenTemplate>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找代码生成模板", description = "根据ID查找代码生成模板")
    public R<GenTemplate> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑代码生成模板", description = "编辑代码生成模板")
        public R<?> edit(@RequestBody GenTemplate entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加代码生成模板", description = "添加代码生成模板")
        public R<?> add(@RequestBody GenTemplate entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除代码生成模板", description = "根据ID删除代码生成模板")
        public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除代码生成模板", description = "批量删除代码生成模板")
        public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

}
