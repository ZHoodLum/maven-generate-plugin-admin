package com.swagger.validation;

import io.swagger.annotations.ApiModel;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.WebDataBinder;
import com.swagger.utils.BoscApplicationContextUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @className: ValidationTargetWrapper
 * @description: 校验值对象 wrapper 包装类
 * @author: caoyj
 * @date: 2021/4/21
 **/
public class ValidationTargetWrapper {
    private Map<Field, ValidationTargetWrapper> targetValidator = new HashMap<>();
    private List<ValidatorTarget> targetReflection;

    public ValidationTargetWrapper(Class<?> target) {
        targetReflection = buildValidatorMap(target);
    }

    /**
     * 构建当前类的 校验对象
     * @param clazz
     * @return
     */
    private List<ValidatorTarget> buildValidatorMap(Class<?> clazz){
        List<ValidatorTarget> vtwList = new ArrayList<>();
        ReflectionUtils.doWithFields(clazz, fcb-> {
            fcb.setAccessible(true);
            Set<Annotation> annotations = AnnotatedElementUtils.findAllMergedAnnotations(fcb,getSupportValidatorAnnotation());
            if( !CollectionUtils.isEmpty(annotations) ){
                vtwList.add(new ValidatorTarget(annotations,fcb));
            }else{
                if( fcb.getType().isAnnotationPresent(ApiModel.class) ){
                    targetValidator.put(fcb, new ValidationTargetWrapper(fcb.getType()));
                }
                Type genericType = getGenericType(fcb.getGenericType());
                if( genericType!=null ){
                    targetValidator.put(fcb, new ValidationTargetWrapper((Class<?>) genericType));
                }
            }
        });
        return vtwList;
    }

    /**
     * 获取 泛型 类型
     * @param genericReturnType
     * @return
     */
    private Type getGenericType(Type genericReturnType){
        Type returnType = null;
        Type[] actualTypeArguments = null;
        if (genericReturnType instanceof ParameterizedType) {
            actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
        }
        if( actualTypeArguments!=null ){
            for (int i = 0; i < actualTypeArguments.length; i++) {
                returnType = actualTypeArguments[i];
            }
        }
        return returnType;
    }

    /**
     * 获取 当前支持的 注解类型
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportValidatorAnnotation(){
        return BoscApplicationContextUtil.getBeansByClass(IValidatorValue.class).values()
                .stream().map(ivv->ivv.support()).collect(Collectors.toSet());
    }

    /**
     * 执行 值对象 的校验逻辑
     * @param binder
     * @param targetValue
     */
    public void validate(WebDataBinder binder,Object targetValue){
        if( !CollectionUtils.isEmpty(targetReflection) ){
            targetReflection.forEach(vt->{
                vt.getFieldTarget().setAccessible(true);
                if( !CollectionUtils.isEmpty(vt.getAnnotation()) ){
                    getValidationImpl().forEach(ivv->{
                        ivv.validate(vt, binder, targetValue);
                    });
                }
            });
        }
        if( !CollectionUtils.isEmpty(targetValidator) ){
            targetValidator.forEach((f,wrapper)->{
                f.setAccessible(true);
                try {
                    wrapper.validate(binder, f.get(targetValue));
                } catch (IllegalAccessException e) {
                }
            });
        }
    }

    private List<IValidatorValue> getValidationImpl(){
        return BoscApplicationContextUtil.getBeansByClass(IValidatorValue.class).values()
                .stream().collect(Collectors.toList());
    }
}
