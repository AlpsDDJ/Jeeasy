package org.jeeasy.system.modules.premission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 系统权限控制器，负责处理与系统权限相关的请求。
 * 提供权限列表的查询，权限的增、删、改操作，以及根据ID获取权限信息。
 *
 * @author AlpsDDJ
 * @since 2020-11-21 13:52:05
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "系统权限")
@RequestMapping("/sys/permission")
public class SysPermissionController extends SimpleBaseController<SysPermissionService, SysPermission> {

    /**
     * 查询所有权限树结构。
     *
     * @return 包含所有权限的树结构列表。
     */
    @GetMapping("/tree")
    @DictTranslation
    @Operation(summary = "权限列表", description = "权限列表")
    public R<List<SysPermission>> tree() {
        return R.ok(service.queryAllChildren(null));
    }

    /**
     * 分页查询权限列表，并以树结构展示。
     *
     * @param queryPageModel 查询条件，包含分页信息和查询参数。
     * @param req            HTTP请求对象，可能包含额外的查询参数。
     * @return 分页后的权限树结构列表。
     */
    @GetMapping
    @DictTranslation
    @Operation(summary = "权限列表", description = "权限列表")
    public R<IPage<SysPermission>> treePage(QueryPageModel queryPageModel, HttpServletRequest req) {
        return R.ok(service.queryPageTreeList(queryPageModel, req));
    }

    /**
     * 根据权限ID获取权限信息。
     *
     * @param id 权限的唯一标识。
     * @return 指定ID的权限信息。
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找权限", description = "根据ID查找权限")
    public R<SysPermission> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * 更新权限信息。
     *
     * @param entity 包含更新后权限信息的对象。
     * @return 更新操作的结果。
     */
    @PutMapping
    @Operation(summary = "编辑权限", description = "编辑权限")
    public R<?> edit(@RequestBody SysPermission entity) {
        return super.update(entity);
    }

    /**
     * 添加新的权限。
     *
     * @param entity 包含新权限信息的对象。
     * @return 添加操作的结果。
     */
    @PostMapping
    @Operation(summary = "添加权限", description = "添加权限")
    public R<?> add(@RequestBody SysPermission entity) {
        return super.insert(entity);
    }

    /**
     * 根据权限ID删除权限。
     *
     * @param id 权限的唯一标识。
     * @return 删除操作的结果。
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除权限", description = "根据ID删除权限")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    /**
     * 批量删除权限。
     *
     * @param ids 权限的唯一标识列表。
     * @return 批量删除操作的结果。
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除权限", description = "批量删除权限")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }
}
