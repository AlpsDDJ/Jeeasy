package org.jeeasy.system.modules.depart.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.system.modules.depart.domain.SysDepart;
import org.jeeasy.system.modules.depart.service.SysDepartService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 组织机构表服务控制器
 *
 * @author AlpsDDJ
 * @description 菜单组织机构
 * @since 2020-11-21 13:52:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = "系统组织机构")
@RequestMapping("/sys/depart")
public class SysDepartController extends SimpleBaseController<SysDepartService, SysDepart> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "组织机构列表", notes = "组织机构列表")
    public R<IPage<SysDepart>> list(HttpServletRequest req) {
        QueryWrapper<SysDepart> wrapper = QueryGenerator.createWrapper(SysDepart.class, req.getParameterMap());
        wrapper.lambda().orderByAsc(SysDepart::getSortNo);
        List<SysDepart> list = service.list(wrapper);
        IPage<SysDepart> page = new Page<>();
        page.setRecords(list);
        return R.ok(page);
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找组织机构", notes = "根据ID查找组织机构")
    public R<SysDepart> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:03
     */
    @PutMapping
    @ApiOperation(value = "编辑组织机构", notes = "编辑组织机构")
    public R<?> edit(@RequestBody SysDepart entity) {
        return super.update(entity);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping
    @ApiOperation(value = "添加组织机构", notes = "添加组织机构")
    public R<?> add(@RequestBody SysDepart entity) {
        service.saveDepartData(entity);
        return R.ok().setResult("添加成功");
    }

    /**
     * @param id
     * @return {@link R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除组织机构", notes = "根据ID删除组织机构")
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
    @ApiOperation(value = "批量删除组织机构", notes = "批量删除组织机构")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}