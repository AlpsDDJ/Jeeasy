// package org.jeeasy.system.api.fallback;
//
// import lombok.Setter;
// import lombok.extern.slf4j.Slf4j;
// import org.jeeasy.common.core.domain.vo.TableDictVo;
// import org.jeeasy.system.api.IDictTranslationApi;
//
// /**
//  * TODO
//  *
//  * @author wei.yang
//  * @date 2023-01-01 21:49
//  */
// @Slf4j
// public class DictTranslationApiFallback implements IDictTranslationApi {
//
//     @Setter
//     private Throwable cause;
//
//     @Override
//     public TableDictVo getTableDictByCode(String code) {
//         log.error("根据 code 获取 TableDict失败: {}", cause);
//         return null;
//     }
//
//     @Override
//     public String translateDictFromTable(TableDictVo tableDict, Object value) {
//         log.error("根据table翻译字典败 {}", cause);
//         return null;
//     }
//
//     @Override
//     public String translateDict(String code, Object value) {
//         log.error("普通字典的翻译失败 {}", cause);
//         return null;
//     }
// }
