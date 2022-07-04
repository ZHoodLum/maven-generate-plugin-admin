package com.swagger.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
/**
 * @author dcits
 * */
@Component
public class BoscApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BoscApplicationContextUtil.applicationContext = applicationContext;
    }

    /**
     * 获取applicationContext对象
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据bean的id来查找对象
     *
     * @param id
     * @return
     */
    public static Object getBeanById(String id) {
        return applicationContext.getBean(id);
    }

    /**
     * 根据bean名获取bean对象
     * @param name
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getBeanByName(String name,Class<T> tClass){
        return applicationContext.getBean(name,tClass);
    }

    /**
     * 根据bean的class来查找对象
     *
     * @param c
     * @return
     */
    public static <T> T getBeanByClass(Class<T> c) {
        return applicationContext.getBean(c);
    }

    /**
     * 根据bean的class来查找所有的对象(包括子类)
     *
     * @param c
     * @return
     */
    public static <T> Map<String,T> getBeansByClass(Class<T> c) {
        return applicationContext.getBeansOfType(c);
    }
}
