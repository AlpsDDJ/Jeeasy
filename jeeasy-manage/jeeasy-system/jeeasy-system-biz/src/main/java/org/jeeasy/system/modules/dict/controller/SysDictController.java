package org.jeeasy.system.modules.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleCurdController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.service.SysDictService;
import org.springframework.web.bind.annotation.*;


/**
 * 数据字典表服务控制器
 *
 * @author AlpsDDJ
 * @description 数据字典
 * @since 2020-11-21 13:52:05
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "数据字典")
@RequestMapping("/sys/dict")
public class SysDictController extends SimpleCurdController<SysDictService, SysDict> {

    //@Resource
    //private CommonService commonService;
    //
    //@GetMapping("/queryByCode/{code}")
    //@DictTranslation
    //@Operation(summary = "型号列表", description = "型号列表")
    //public R<IPage<SysDict>> queryDictByCode(@PathVariable("code") String code, @RequestParam(value = "pid", required = false) String pid) {
    //    //service.queryByParentCode()
    //    commonService.getDictsByCode(code, pid, false);
    //    return null;
    //}

    @GetMapping
    @DictTranslation
    @Operation(summary = "数据字典列表", description = "数据字典列表")
    public R<IPage<SysDict>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找数据字典", description = "根据ID查找数据字典")
    public R<SysDict> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:03
     */
    @PutMapping
    @Operation(summary = "编辑数据字典", description = "编辑数据字典")
    public R<?> edit(@RequestBody SysDict entity) {
        return super.update(entity);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping
    @Operation(summary = "添加数据字典", description = "添加数据字典")
    public R<?> add(@RequestBody SysDict entity) {
        return super.insert(entity);
    }

    /**
     * @param id
     * @return {@link R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除数据字典", description = "根据ID删除数据字典")
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
    @Operation(summary = "批量删除数据字典", description = "批量删除数据字典")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }
}