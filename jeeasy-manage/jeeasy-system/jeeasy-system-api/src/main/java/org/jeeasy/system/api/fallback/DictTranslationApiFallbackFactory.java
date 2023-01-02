// package org.jeeasy.system.api.factory;
//
// import org.jeeasy.system.api.IDictTranslationApiFeign;
// import org.jeeasy.system.api.fallback.DictTranslationApiFallback;
// import org.springframework.cloud.openfeign.FallbackFactory;
// import org.springframework.stereotype.Component;
//
// /**
//  * DictTranslationApiFallbackFactory
//  *
//  * @author wei.yang
//  * @date 2023-01-01 21:48
//  */
// @Component
// public class DictTranslationApiFallbackFactory  implements FallbackFactory<IDictTranslationApiFeign> {
//     @Override
//     public IDictTranslationApiFeign create(Throwable cause) {
//         DictTranslationApiFallback fallback = new DictTranslationApiFallback();
//         fallback.setCause(cause);
//         return fallback;
//     }
// }
