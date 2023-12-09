package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.service.GenTableService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Tag(name = "代码生成-表信息")
@RequestMapping("/gen/table")
public class GenTableController extends SimpleBaseController<GenTableService, GenTable> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "表信息列表")
    public R<IPage<GenTable>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找表信息")
    public R<GenTable> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑表信息")
    public R<?> edit(@RequestBody GenTable entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加表信息")
    public R<?> add(@RequestBody GenTable entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除表信息")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除表信息")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

}
