package com.mojo.utils;

import com.mojo.constant.ExcelConstant;
import com.mojo.constant.SpringAnnotationConstant;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/17 21:30
 * @Description:thoughtworks 提取类/接口/方法定义（包括注释，参数，参数名称）
 */
public class ClassSourceBuilderUtil {
    private JavaProjectBuilder builder;

    private Collection<JavaClass> javaClasses;

    public ClassSourceBuilderUtil(String path) {
        builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(path));
        this.javaClasses = builder.getClasses();
    }

    public Set<JavaClass> getControllerData() {
        Set<JavaClass> apiMethodDocs = new HashSet<>();
        for (JavaClass javaClass : javaClasses) {
            if (isController(javaClass)) {
                apiMethodDocs.add(javaClass);
            }
        }
        return apiMethodDocs;
    }


    /**
     * 是否为controller
     *
     * @param cls cls
     * @return true or false
     */
    public static boolean isController(JavaClass cls) {
        List<JavaAnnotation> classAnnotations = cls.getAnnotations();
        for (JavaAnnotation annotation : classAnnotations) {
            String annotationName = annotation.getType().getName();
            if (SpringAnnotationConstant.CONTROLLER_SET.contains(annotationName)) {
                return true;
            }
        }
        return false;
    }

}
