package me.superning.nettychat.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author superning
 * 解决netty的Handler 在spring下使用@Service注解不能注入的问题
 */
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext==null) {
            SpringUtil.applicationContext = applicationContext;

        }
    }
    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;

    }

    public static Object getBean(String name)
    {
        return getApplicationContext().getBean(name);

    }
    public static <T> T getBean(Class<T> clazz)
    {
        return getApplicationContext().getBean(clazz);

    }
    public static <T> T getBean(String name,Class<T> clazz)
    {
        return getApplicationContext().getBean(name,clazz);

    }
}
