package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.domain.GenModule;
import org.jeeasy.generate.service.GenModuleService;
import org.springframework.web.bind.annotation.*;

/**
 * @author AlpsDDJ
 * @date 2023/12/14
 */
@RestController
@Tag(name = "代码生成-模块信息")
@RequestMapping("/gen/module")
public class GenModuleController extends SimpleBaseController<GenModuleService, GenModule> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "模块信息列表")
    public R<IPage<GenModule>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找模块信息")
    public R<GenModule> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @GetMapping("/findByCode/{code}")
    @DictTranslation
    @Operation(summary = "根据code查找模块信息")
    public R<GenModule> findByCode(@PathVariable("code") String code) {
        return R.ok(service.getByCode(code));
    }

    @PutMapping
    @Operation(summary = "编辑模块信息")
    public R<?> edit(@RequestBody GenModule entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加模块信息")
    public R<?> add(@RequestBody GenModule entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除模块信息")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除模块信息")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

}
