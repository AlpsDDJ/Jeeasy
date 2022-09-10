package org.jeeasy.system.modules.premission.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单权限表服务控制器
 *
 * @author AlpsDDJ
 * @since 2020-11-21 13:52:05
 * @description 菜单权限
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "系统权限")
@RequestMapping("/sys/permission")
public class SysPermissionController extends SimpleBaseController<SysPermissionService, SysPermission> {


    @GetMapping
    @DictTranslation
    @Operation(summary = "权限列表", description = "权限列表")
    public R<List<SysPermission>> list() {
//        QueryWrapper<SysPermission> wrapper = QueryGenerator.createWrapper(SysPermission.class, req.getParameterMap());
//        wrapper.lambda().orderByAsc(SysPermission::getSortNo);
//        String customSqlSegment = wrapper.getCustomSqlSegment();
//        log.info("customSqlSegment ======== {}", customSqlSegment);
//        List<SysPermission> list = service.list(wrapper);
//        IPage<SysPermission> page = new Page<>();
//        page.setRecords(list);
//        return R.ok(page);
        return R.ok(service.queryAllChildren(null));
    }

    /**
     * @author mobie
     * @date 2020/11/21 16:05
     * @param id
     * @return {@link R}
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找权限", description = "根据ID查找权限")
    public R<SysPermission> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @author mobie
     * @date 2020/11/21 16:03
     * @param entity
     * @return {@link R}
     */
    @PutMapping
    @Operation(summary = "编辑权限", description = "编辑权限")
    public R<?> edit(@RequestBody SysPermission entity) {
        return super.update(entity);
    }

    /**
     * @author mobie
     * @date 2020/11/21 16:18
     * @param entity
     * @return {@link R}
     */
    @PostMapping
    @Operation(summary = "添加权限", description = "添加权限")
    public R<?> add(@RequestBody SysPermission entity) {
        return super.insert(entity);
    }

    /**
     *
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     * @param id
     * @return {@link org.jeeasy.common.core.domain.vo.R<?>}
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除权限", description = "根据ID删除权限")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    /**
     * @author mobie
     * @date 2020/11/21 16:10
     * @param ids
     * @return {@link R}
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除权限", description = "批量删除权限")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}