package org.jeeasy.system.modules.common.controller;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.service.CommonService;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 10:23
 */
@Slf4j
@RestController
@Api(tags = "系统 公共模块")
@RequestMapping("/common")
public class SystemCommonController {

    @Resource
    private CommonService commonService;

    /**
     * 根据ID查找数据字典
     *
     * @param code     代码
     * @param parentId 父id
     * @param async    异步
     * @return {@link R}<{@link List}<{@link DictVo}>>
     * @author Alps
     * @date 2020/11/21 16:05
     */
    @GetMapping("/dicts/{code}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找数据字典", notes = "根据ID查找数据字典")
    public R<List<DictVo>> getDictsById(@PathVariable("code") String code, @RequestParam(required = false) String parentId, @RequestParam(required = false, defaultValue = "false") String async) {
        return R.ok(commonService.getDictsByCode(code, parentId, Boolean.parseBoolean(async)));
    }

//    /**
//     * @param code
//     * @return {@link R}
//     * @author Alps
//     * @date 2020/11/21 16:05
//     */
//    @GetMapping("/dicts/parents/{code}/{value}")
//    @DictTranslation
//    @ApiOperation(value = "根据ID查找数据字典", notes = "根据ID查找数据字典")
//    public R<List<DictVo>> parents(@PathVariable("code") String code, @PathVariable("value") String value) {
////        return R.ok(commonService.getDictsByCode(code, parentId));
//        return R.ok();
//    }
}

