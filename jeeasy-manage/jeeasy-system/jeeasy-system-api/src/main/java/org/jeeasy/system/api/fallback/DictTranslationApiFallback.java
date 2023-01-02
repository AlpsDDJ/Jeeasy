// package org.jeeasy.system.api.fallback;
//
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;
// import org.jeeasy.common.core.domain.dto.TranslateDictDTO;
// import org.jeeasy.common.core.domain.dto.TranslateDictFromTableDTO;
// import org.jeeasy.common.core.domain.vo.R;
// import org.jeeasy.common.core.domain.vo.TableDictVo;
// import org.jeeasy.system.api.IDictTranslationApiFeign;
//
// /**
//  * TODO
//  *
//  * @author wei.yang
//  * @date 2023-01-01 21:49
//  */
// @Slf4j
// public class DictTranslationApiFallback implements IDictTranslationApiFeign {
//
//     @Setter
//     private Throwable cause;
//
//     @Override
//     public R<TableDictVo> getTableDictByCode(String code) {
//         log.error("根据 code 获取 TableDict失败: {}", cause);
//         return null;
//     }
//
//     @Override
//     public R<String> translateDictFromTable(TranslateDictFromTableDTO dto) {
//         log.error("根据table翻译字典败 {}", cause);
//         return null;
//     }
//
//     @Override
//     public R<String> translateDict(TranslateDictDTO dto) {
//         log.error("普通字典的翻译失败 {}", cause);
//         return null;
//     }
// }
