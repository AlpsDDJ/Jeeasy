package org.jeeasy.system.api;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.dto.TranslateDictDTO;
import org.jeeasy.common.core.domain.dto.TranslateDictFromTableDTO;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.IDictTranslationService;
import org.springframework.web.bind.annotation.*;


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
    private IDictTranslationService dictTranslationService;

    @GetMapping("/getTableDictByCode")
    public R<TableDictVo> getTableDictByCode(@RequestParam("code") String code) {
        return dictTranslationService.getTableDictByCode(code);
    }

    @PostMapping("/translateDictFromTable")
    public R<String> translateDictFromTable(@RequestBody TranslateDictFromTableDTO dto) {
        return dictTranslationService.translateDictFromTable(dto);
    }

    @PostMapping("/translateDict")
    public R<String> translateDict(@RequestBody TranslateDictDTO dto) {
        return dictTranslationService.translateDict(dto);
    }
}
