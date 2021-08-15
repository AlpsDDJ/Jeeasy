package org.jeeasy.system.modules.dict.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.db.annotation.DictTranslation;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.base.SimpleBaseController;
import org.jeeasy.common.db.model.QueryPageModel;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.service.SysDictService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
@Api(tags = "数据字典")
@RequestMapping("/sys/dict")
public class SysDictController extends SimpleBaseController<SysDictService, SysDict> {

    @GetMapping
    @DictTranslation
    @ApiOperation(value = "数据字典列表", notes = "数据字典列表")
    public R<IPage<SysDict>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.query(queryPageModel, req, SysDict.class);
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找数据字典", notes = "根据ID查找数据字典")
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
    @ApiOperation(value = "编辑数据字典", notes = "编辑数据字典")
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
    @ApiOperation(value = "添加数据字典", notes = "添加数据字典")
    public R<?> add(@RequestBody SysDict entity) {
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
    @ApiOperation(value = "根据ID删除数据字典", notes = "根据ID删除数据字典")
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
    @ApiOperation(value = "批量删除数据字典", notes = "批量删除数据字典")
    public R<?> removeBatch(@RequestParam(name = "ids") String ids) {
        return super.deleteBatch(ids);
    }
}