package org.jeeasy.system.api;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.system.modules.common.service.impl.DictTranslationServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2023-01-02 0:05
 */
@Slf4j
@RestController
@RequestMapping("/sys/api/dict")
public class DictTranslationApi {

    @Resource
    private DictTranslationServiceImpl dictTranslationService;

    @PostMapping("/getTableDictByCode")
    public R<TableDictVo> getTableDictByCode(String code) {
        return dictTranslationService.getTableDictByCode(code);
    }

    @PostMapping("/translateDictFromTable")
    public R<String> translateDictFromTable(TableDictVo tableDict, Object value) {
        return dictTranslationService.translateDictFromTable(tableDict, value);
    }

    @PostMapping("/translateDict")
    public R<String> translateDict(String code, Object value) {
        return dictTranslationService.translateDict(code, value);
    }
}
