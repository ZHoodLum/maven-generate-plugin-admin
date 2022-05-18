package com.example.util;

import java.lang.annotation.*;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/17 22:25
 * @Description:
 */
@Target(value = ElementType.METHOD)
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface LogAPI {
    /**
     * 默认值
     * @return
     */
    String code() default "默认值";

    /**
     * 可选值范围 使用 , 分割多个可选值
     * @return
     */
    String[] name() default {};
}
