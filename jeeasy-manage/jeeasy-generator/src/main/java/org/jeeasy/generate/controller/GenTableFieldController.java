package org.jeeasy.generate.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
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
@Tag(name = "代码生成-表字段信息")
@RequestMapping("/gen/tableField")
public class GenTableFieldController extends SimpleBaseController<GenTableFieldService, GenTableField> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "表字段信息列表")
    public R<List<GenTableField>> list(HttpServletRequest req) {
        QueryWrapper<GenTableField> queryWrapper = QueryGenerator.createWrapper(GenTableField.class, req.getParameterMap());
        List<GenTableField> fields = service.list(queryWrapper);
        return R.ok(fields);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找表字段信息")
    public R<GenTableField> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑表字段信息")
    public R<?> edit(@RequestBody GenTableField entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加表字段信息")
    public R<?> add(@RequestBody GenTableField entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除表字段信息")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除表字段信息")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

}
