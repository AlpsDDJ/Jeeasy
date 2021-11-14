package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.generate.domain.GenTableField;
import org.jeeasy.generate.service.GenTableFieldService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@RestController
@Api(tags = "代码生成-表字段信息")
@RequestMapping("/gen/tableField")
public class GenTableFieldController extends SimpleBaseController<GenTableFieldService, GenTableField> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "表字段信息列表", notes = "表字段信息列表")
    public R<List<GenTableField>> list(HttpServletRequest req) {
        QueryWrapper<GenTableField> queryWrapper = QueryGenerator.createWrapper(GenTableField.class, req.getParameterMap());
        List<GenTableField> fields = service.list(queryWrapper);
        return R.ok(fields);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找表字段信息", notes = "根据ID查找表字段信息")
    public R<GenTableField> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @ApiOperation(value = "编辑表字段信息", notes = "编辑表字段信息")
    public R<?> edit(@RequestBody GenTableField entity) {
        return super.update(entity);
    }

    @PostMapping
    @ApiOperation(value = "添加表字段信息", notes = "添加表字段信息")
    public R<?> add(@RequestBody GenTableField entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除表字段信息", notes = "根据ID删除表字段信息")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除表字段信息", notes = "批量删除表字段信息")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

}
