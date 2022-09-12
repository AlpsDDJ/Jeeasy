package org.jeeasy.system.modules.dept.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.dept.service.SysDeptService;
import org.springframework.web.bind.annotation.*;

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
@Tag(name = "系统组织机构")
@RequestMapping("/sys/dept")
public class SysDeptController extends SimpleBaseController<SysDeptService, SysDept> {

    @GetMapping
    @DictTranslation
    @Operation(summary =  "组织机构列表", description = "组织机构列表")
    public R<List<SysDept>> list() {
//        QueryWrapper<SysDept> wrapper = QueryGenerator.createWrapper(SysDept.class, req.getParameterMap());
//        wrapper.lambda().orderByAsc(SysDept::getSortNo);
//        List<SysDept> list = service.list(wrapper);
//        IPage<SysDept> page = new Page<>();
//        page.setRecords(list);
//        return R.ok(page);
        return R.ok(service.queryAllChildren(null));
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary =  "根据ID查找组织机构", description = "根据ID查找组织机构")
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
    @Operation(summary =  "编辑组织机构", description = "编辑组织机构")
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
    @Operation(summary =  "添加组织机构", description = "添加组织机构")
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
    @Operation(summary =  "根据ID删除组织机构", description = "根据ID删除组织机构")
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
    @Operation(summary =  "批量删除组织机构", description = "批量删除组织机构")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}