package com.mojo.handles.annotation;

import java.lang.annotation.*;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/7/4 17:22
 * @Description: 注解 用于辨别字段属于什么类型  普通字段或者数组\集合
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface EntityParamType {
    /**
     * @return
     */
    String sourceType();
}
