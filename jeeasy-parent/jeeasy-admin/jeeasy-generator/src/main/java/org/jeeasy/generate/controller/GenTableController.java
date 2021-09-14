package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.generate.entity.GenTable;
import org.jeeasy.generate.service.GenTableService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Api(tags = "代码生成-表信息")
@RequestMapping("/gen/table")
public class GenTableController extends SimpleBaseController<GenTableService, GenTable> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "表信息列表", notes = "表信息列表")
    public R<IPage<GenTable>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.query(queryPageModel, req, GenTable.class);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找表信息", notes = "根据ID查找表信息")
    public R<GenTable> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "编辑表信息", notes = "编辑表信息")
    public R<?> edit(@RequestBody GenTable entity) {
        return super.update(entity);
    }

    @PostMapping
    @ApiOperation(value = "添加表信息", notes = "添加表信息")
    public R<?> add(@RequestBody GenTable entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除表信息", notes = "根据ID删除表信息")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除表信息", notes = "批量删除表信息")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

}
