package com.mojo.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/18 9:20
 * @Description:Annotation全局静态类
 */
public class AnnotationConstant {
    /**
     * 所有controller
     */
    public static final Set<String> ANNOTATION_SET = new HashSet<>();
    /**
     * controller注解全名称
     */
    public static final String CONTROLLER_FULLY = "org.springframework.stereotype.Controller";
    /**
     * rest controller注解全名称
     */
    public static final String REST_CONTROLLER_FULLY = "org.springframework.web.bind.annotation.RestController";

    /**
     * 初始化Spring枚举到Http方法的对应
     */
    static {
        ANNOTATION_SET.add("Controller");
        ANNOTATION_SET.add("RestController");
        ANNOTATION_SET.add(REST_CONTROLLER_FULLY);
        ANNOTATION_SET.add(CONTROLLER_FULLY);
    }
}
