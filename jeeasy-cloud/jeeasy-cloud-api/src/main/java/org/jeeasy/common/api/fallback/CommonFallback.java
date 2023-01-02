package org.jeeasy.common.api.fallback;

import org.jeeasy.common.core.domain.vo.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.core.ResolvableType;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2023-01-01 22:22
 */
public interface CommonFallback<T> extends FallbackFactory<T> {

    Logger logger = LoggerFactory.getLogger(CommonFallback.class);

    @Override
    default T create(Throwable cause) {
        ResolvableType resolvableType = ResolvableType.forClass(this.getClass());
        ResolvableType superType = resolvableType.getSuperType();
        final Class<?> clazz = superType.getGeneric(0).getRawClass();
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            logger.error("服务降级：{} - {}: {}", clazz.getName(), method.getName(), cause);
            return R.error(cause.getMessage());
        });
    }
}
