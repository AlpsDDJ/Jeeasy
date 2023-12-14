package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.domain.GenTableField;
import org.jeeasy.generate.domain.GenTableIndex;
import org.jeeasy.generate.service.GenTableFieldService;
import org.jeeasy.generate.service.GenTableIndexService;
import org.jeeasy.generate.service.GenTableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Tag(name = "代码生成-表信息")
@RequestMapping("/gen/table")
public class GenTableController extends SimpleBaseController<GenTableService, GenTable> {

    @Resource
    GenTableFieldService tableFieldService;
    @Resource
    GenTableIndexService tableIndexService;

    @GetMapping
    @DictTranslation
    @Operation(summary = "表信息列表")
    public R<IPage<GenTable>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        //R<IPage<GenTable>> page = super.queryPage(queryPageModel, req);
        Page<GenTable> page = service.page(queryPageModel.getPage(GenTable.class), getWrapper(req));
        page.getRecords().forEach(item -> {
            String tid = item.getId();
            List<GenTableField> tableFields = tableFieldService.list(new QueryWrapper<GenTableField>().lambda().eq(GenTableField::getTableId, tid));
            item.setTableFields(tableFields);
            List<GenTableIndex> tableIndexs = tableIndexService.list(new QueryWrapper<GenTableIndex>().lambda().eq(GenTableIndex::getTableId, tid));
            item.setTableIndexs(tableIndexs);
        });
        return R.ok(page);
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
        return R.ok(service.updateWithFields(entity));
    }

    @PostMapping
    @Operation(summary = "添加表信息")
    public R<?> add(@RequestBody GenTable entity) {
        return R.ok(service.saveWithFields(entity));
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
