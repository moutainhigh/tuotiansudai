package com.tuotiansudai.paywrapper.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBeanByType(Class<T> beanClass) {
        return SpringContextUtil.applicationContext.getBean(beanClass);
    }

    public static Object getBeanByName(String beanName) {
        return SpringContextUtil.applicationContext.getBean(beanName);
    }
}