package org.jeeasy.system.api;

import org.jeeasy.common.api.fallback.CommonFallback;
import org.jeeasy.common.core.config.constant.ServiceNameConstant;
import org.jeeasy.common.core.domain.dto.TranslateDictDTO;
import org.jeeasy.common.core.domain.dto.TranslateDictFromTableDTO;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.IDictTranslationService;
import org.jeeasy.system.api.config.FeignConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 9:43
 */
@Component
@FeignClient(path = "sys/api", contextId = "dictTranslationApi", value = ServiceNameConstant.SERVICE_SYSTEM, fallbackFactory = IDictTranslationApiFeign.DictTranslationApiFallbackFactory.class, configuration = FeignConfiguration.class)
@ConditionalOnMissingClass("org.jeeasy.system.modules.common.service.impl.DictTranslationServiceImpl")
public interface IDictTranslationApiFeign extends IDictTranslationService {

    @Component
    class DictTranslationApiFallbackFactory implements CommonFallback<IDictTranslationApiFeign> {
    }

    /**
     * 根据 code 获取 TableDict
     * @param code
     * @return
     */
    @GetMapping("dict/getTableDictByCode")
    R<TableDictVo> getTableDictByCode(@RequestParam("code") String code);

    /**
     * 根据table翻译字典
     *
     * @param tableDict
     * @param value
     * @return
     */
    @PostMapping("dict/translateDictFromTable")
    R<String> translateDictFromTable(@RequestBody TranslateDictFromTableDTO dto);

    /**
     * 普通字典的翻译
     *
     * @param code
     * @param value
     * @return
     */
    @PostMapping("dict/translateDict")
    R<String> translateDict(@RequestBody TranslateDictDTO dto);
}
