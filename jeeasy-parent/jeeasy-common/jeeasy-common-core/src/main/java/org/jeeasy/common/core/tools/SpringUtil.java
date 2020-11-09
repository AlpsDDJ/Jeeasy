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
@Component
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
//        if (applicationContext.containsBean(name)) {
//            Object bean = applicationContext.getBean(name);
//            if (bean.getClass().isAssignableFrom(clazz)) {
//                return (T) bean;
//            } else {
//                throw new RuntimeException("BeanName 重复 " + name);
//            }
//        }
//
//
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//        for (Object arg : args) {
//            beanDefinitionBuilder.addConstructorArgValue(arg);
//        }
//        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
//
//        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getAutowireCapableBeanFactory();
//        beanFactory.registerBeanDefinition(name, beanDefinition);
//        return applicationContext.getBean(name, clazz);



//        DefaultListableBeanFactory autowireCapableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
//        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Animal.class);
//        beanDefinitionBuilder.addConstructorArgValue("喜鹊").addConstructorArgValue("绿色").addConstructorArgValue(3);
//        String beanName = "AnimalAutoName";
//        //默认单例
//        beanDefinitionBuilder.setScope("prototype");
//        autowireCapableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
//        Object bean = run.getBean(beanName);
//        Animal animal = (Animal) bean;
//        System.out.println(animal.getAge());
//        System.out.println(animal.getColor());
//        System.out.println(animal.getName());


        AnnotationConfigServletWebServerApplicationContext context = (AnnotationConfigServletWebServerApplicationContext) SpringUtil.applicationContext;

        if(SpringUtil.applicationContext.containsBeanDefinition(name)){
            context.removeBeanDefinition(name);
        } else {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(object.getClass());
            context.registerBeanDefinition(name, beanDefinitionBuilder.getRawBeanDefinition());
        }
        context.getBeanFactory().registerSingleton(name, object);
        return object;
    }

}
