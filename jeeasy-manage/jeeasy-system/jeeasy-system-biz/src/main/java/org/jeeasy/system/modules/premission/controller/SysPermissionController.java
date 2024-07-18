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
 * 菜单权限表服务控制器
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


    @GetMapping("/tree")
    @DictTranslation
    @Operation(summary = "权限列表", description = "权限列表")
    public R<List<SysPermission>> tree() {
        return R.ok(service.queryAllChildren(null));
    }


    @GetMapping
    @DictTranslation
    @Operation(summary = "权限列表", description = "权限列表")
    public R<IPage<SysPermission>> treePage(QueryPageModel queryPageModel, HttpServletRequest req) {
        return R.ok(service.queryPageTreeList(queryPageModel, req));
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找权限", description = "根据ID查找权限")
    public R<SysPermission> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:03
     */
    @PutMapping
    @Operation(summary = "编辑权限", description = "编辑权限")
    public R<?> edit(@RequestBody SysPermission entity) {
        return super.update(entity);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping
    @Operation(summary = "添加权限", description = "添加权限")
    public R<?> add(@RequestBody SysPermission entity) {
        return super.insert(entity);
    }

    /**
     * @param id
     * @return {@link org.jeeasy.common.core.domain.vo.R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除权限", description = "根据ID删除权限")
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
    @Operation(summary = "批量删除权限", description = "批量删除权限")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }
}