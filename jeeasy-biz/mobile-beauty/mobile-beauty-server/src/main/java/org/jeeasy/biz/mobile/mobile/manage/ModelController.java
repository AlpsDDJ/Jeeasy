package org.jeeasy.biz.mobile.mobile.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeeasy.biz.mobile.modules.model.domain.MbModel;
import org.jeeasy.biz.mobile.modules.model.service.MbModelService;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @date 2023/12/6
 */
@RestController
@Tag(name = "型号")
@RequestMapping("/mb/model")
public class ModelController extends SimpleBaseController<MbModelService, MbModel> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "型号列表", description = "型号列表")
    public R<IPage<MbModel>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找型号", description = "根据ID查找型号")
    public R<MbModel> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑型号", description = "编辑型号")
    public R<?> edit(@RequestBody MbModel entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加型号", description = "添加型号")
    public R<?> add(@RequestBody MbModel entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除型号", description = "根据ID删除型号")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除型号", description = "批量删除型号")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }

}
