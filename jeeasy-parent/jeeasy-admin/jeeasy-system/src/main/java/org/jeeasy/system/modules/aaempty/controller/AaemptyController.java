package org.jeeasy.system.modules.aaempty.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.system.modules.aaempty.domain.Aaempty;
import org.jeeasy.system.modules.aaempty.service.AaemptyService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 空白模板表服务控制器
 *
 * @author AlpsDDJ
 * @description 空白模板
 * @since 2020-11-21 13:52:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "空白模板")
@RequestMapping("/aaempty")
public class AaemptyController extends SimpleBaseController<AaemptyService, Aaempty> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "空白模板列表", notes = "空白模板列表")
    public R<IPage<Aaempty>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.query(queryPageModel, req, Aaempty.class);
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找空白模板", notes = "根据ID查找空白模板")
    public R<Aaempty> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:03
     */
    @PutMapping
    @ApiOperation(value = "编辑空白模板", notes = "编辑空白模板")
    public R<?> edit(@RequestBody Aaempty entity) {
        return super.update(entity);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping
    @ApiOperation(value = "添加空白模板", notes = "添加空白模板")
    public R<?> add(@RequestBody Aaempty entity) {
        super.insert(entity);
        return R.ok().setResult("添加成功");
    }

    /**
     * @param id
     * @return {@link R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除空白模板", notes = "根据ID删除空白模板")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    /**
     * @param ids
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:10
     */
    @DeleteMapping("/batch")
    @ApiOperation(value = "批量删除空白模板", notes = "批量删除空白模板")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}