package org.jeeasy.system.modules.common.controller;

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
     * @param code
     * @return {@link R}
     * @author Alps
     * @date 2020/11/21 16:05
     */
    @GetMapping("/dicts/{code}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找数据字典", notes = "根据ID查找数据字典")
    public R<List<DictVo>> getDicts(@PathVariable("code") String code, @RequestParam(required = false) String parentId) {
        return R.ok(commonService.getDictsByCode(code, parentId));
    }
}
