package org.jeeasy.system.modules.premission.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.base.SimpleBaseController;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@Api(tags = "系统权限")
@RequestMapping("/sys/permission")
public class SysPermissionController extends SimpleBaseController<SysPermissionService, SysPermission> {


    @GetMapping
    @DictTranslation
    @ApiOperation(value = "权限列表", notes = "权限列表")
    public R<IPage<SysPermission>> list(SysPermission entity, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        return super.query(entity, pageNo, pageSize, req);
    }

    /**
     * @author mobie
     * @date 2020/11/21 16:05
     * @param id
     * @return {@link R}
     */
    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找权限", notes = "根据ID查找权限")
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
    @ApiOperation(value = "编辑权限", notes = "编辑权限")
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
    @ApiOperation(value = "添加权限", notes = "添加权限")
    public R<?> add(@RequestBody SysPermission entity) {
        return super.insert(entity);
    }

    /**
     *
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     * @param id
     * @return {@link org.jeeasy.common.core.vo.R<?>}
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除权限", notes = "根据ID删除权限")
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
    @ApiOperation(value = "批量删除权限", notes = "批量删除权限")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}