package com.swagger.validation;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @className: AbstractValidatorValue
 * @description: 值 验证器抽象类
 * @author: caoyj
 * @date: 2021/4/21
 **/
public abstract class AbstractValidatorValue implements IValidatorValue{

    @Override
    public void validate(ValidatorTarget target, WebDataBinder binder, Object validateValue) {
        Annotation annotation = target.getAnnotation().stream().filter(clazz->support().isAssignableFrom(clazz.annotationType())).findAny().orElse(null);
        if( validateValue!=null ){
            if( validateValue.getClass().isArray() ){
                List<Object> values = new ArrayList<>();
                CollectionUtils.mergeArrayIntoCollection(validateValue, values);
                values.forEach(value->{
                    try {
                        validateObject(value.getClass(), target.getFieldTarget().get(value),target.getFieldTarget().getName(), annotation, binder);
                    } catch (IllegalAccessException e) {
                    }
                });
                return;
            }
            if(Collection.class.isInstance(validateValue)){
                Collection<Object> values = (Collection<Object>) validateValue;
                values.forEach(value->{
                    try {
                        validateObject(value.getClass(), target.getFieldTarget().get(value),target.getFieldTarget().getName(), annotation, binder);
                    } catch (IllegalAccessException e) {
                    }
                });
                return;
            }
            if( Map.class.isInstance(validateValue) ){
                Map<String,Object> map = (Map<String, Object>) validateValue;
                map.values().forEach(value->{
                    try {
                        validateObject(value.getClass(), target.getFieldTarget().get(value),target.getFieldTarget().getName(), annotation, binder);
                    } catch (IllegalAccessException e) {
                    }
                });
                return;
            }
        }
        try {
            if( validateValue==null ){
                validateObject(target.getFieldTarget().getType(), null, target.getFieldTarget().getName(), annotation, binder);
            }else{
                validateObject(validateValue.getClass(), target.getFieldTarget().get(validateValue), target.getFieldTarget().getName(), annotation, binder);
            }
        } catch (IllegalAccessException e) {
        }
    }

    public ObjectError addError(Class<?> targetClass, String defaultMessage){
        return new ObjectError(targetClass.getName(), defaultMessage);
    }

    public abstract void validateObject(Class<?> targetClass, Object fieldValue, String fieldName, Annotation annotation, WebDataBinder binder);
}
