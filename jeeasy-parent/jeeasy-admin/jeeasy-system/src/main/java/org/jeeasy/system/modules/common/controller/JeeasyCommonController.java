package org.jeeasy.system.modules.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.DictTranslation;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.tools.DictUtil;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.system.modules.common.vo.DictVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 10:23
 */
@Slf4j
@RestController
@Api(tags = "Jeeasy 公共模块")
@RequestMapping("/common")
public class JeeasyCommonController {


    /**
     * @param code
     * @return {@link R}
     * @author Alps
     * @date 2020/11/21 16:05
     */
    @GetMapping("/dicts/{parentCode}")
    @DictTranslation
    @ApiOperation(value = "根据ID查找数据字典", notes = "根据ID查找数据字典")
    public R<List<DictVo>> getDicts(@PathVariable("parentCode") String code) {
        List<IDictEnum<?>> dictEnum = DictUtil.getDictEnum(code);
        List<DictVo> dicts = new ArrayList<>();

        dictEnum.stream().forEach(de -> {
            dicts.add(new DictVo(de));
        });

        return R.ok(dicts);
    }
}
