package org.jeeasy.system.modules.dept.controller;

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
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.dept.service.SysDeptService;
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
@RequestMapping("/sys/dept")
public class SysDeptController extends SimpleBaseController<SysDeptService, SysDept> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "组织机构列表", notes = "组织机构列表")
    public R<IPage<SysDept>> list(HttpServletRequest req) {
        QueryWrapper<SysDept> wrapper = QueryGenerator.createWrapper(SysDept.class, req.getParameterMap());
        wrapper.lambda().orderByAsc(SysDept::getSortNo);
        List<SysDept> list = service.list(wrapper);
        IPage<SysDept> page = new Page<>();
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
    public R<SysDept> info(@PathVariable("id") String id) {
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
    public R<?> edit(@RequestBody SysDept entity) {
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
    public R<?> add(@RequestBody SysDept entity) {
        service.saveDeptData(entity);
        return R.ok().setData("添加成功");
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