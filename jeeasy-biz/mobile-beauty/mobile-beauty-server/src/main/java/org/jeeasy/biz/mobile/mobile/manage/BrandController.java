package org.jeeasy.biz.mobile.mobile.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jeeasy.biz.mobile.modules.brand.domain.MbBrand;
import org.jeeasy.biz.mobile.modules.brand.service.MbBrandService;
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
@Tag(name = "品牌")
@RequestMapping("/mb/brand")
public class BrandController extends SimpleBaseController<MbBrandService, MbBrand> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "品牌列表", description = "品牌列表")
    public R<IPage<MbBrand>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找品牌", description = "根据ID查找品牌")
    public R<MbBrand> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    @PutMapping
    @Operation(summary = "编辑品牌", description = "编辑品牌")
    public R<?> edit(@RequestBody MbBrand entity) {
        return super.update(entity);
    }

    @PostMapping
    @Operation(summary = "添加品牌", description = "添加品牌")
    public R<?> add(@RequestBody MbBrand entity) {
        return super.insert(entity);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除品牌", description = "根据ID删除品牌")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    @DeleteMapping("/batch")
    @Operation(summary = "批量删除品牌", description = "批量删除品牌")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }

}
