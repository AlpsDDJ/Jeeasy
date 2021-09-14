package org.jeeasy.common.core.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 实现ApplicationContextAware接口，并加入Component注解，让spring扫描到该bean
 * 该类用于在普通Java类中注入bean,普通Java类中用@Autowired是无法注入bean的
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Component("SpringUtil")
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);

    }

    /**
     * 主动向Spring容器中注册bean
     *
     * @param name               BeanName
     * @param <T>
     * @return 返回注册到容器中的bean对象
     */
    public static <T> T registerBean(String name, T object) {
        AnnotationConfigServletWebServerApplicationContext context = (AnnotationConfigServletWebServerApplicationContext) applicationContext;

        if(context.containsBeanDefinition(name)){
            context.removeBeanDefinition(name);
        } else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(object.getClass());
            context.registerBeanDefinition(name, beanDefinitionBuilder.getRawBeanDefinition());
        }
        context.getBeanFactory().registerSingleton(name, object);
        return object;
    }

}
