package com.swagger.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * @className: ValidatorTarget
 * @description: 待校验的 对象
 * @author: caoyj
 * @date: 2021/4/21
 **/
public class ValidatorTarget {
    private Set<Annotation> annotation;
    private Field fieldTarget;

    public ValidatorTarget(Set<Annotation> annotation, Field fieldTarget) {
        this.annotation = annotation;
        this.fieldTarget = fieldTarget;
        this.fieldTarget.setAccessible(true);
    }

    public Set<Annotation> getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Set<Annotation> annotation) {
        this.annotation = annotation;
    }

    public Field getFieldTarget() {
        return fieldTarget;
    }

    public void setFieldTarget(Field fieldTarget) {
        this.fieldTarget = fieldTarget;
    }
}
