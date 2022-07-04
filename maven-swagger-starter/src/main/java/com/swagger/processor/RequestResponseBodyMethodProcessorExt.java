package com.swagger.processor;

import com.swagger.validation.ValidationTargetWrapper;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: RequestResponseBodyMethodProcessorExt
 * @description: RequestResponseBodyMethodProcessor 扩展类
 * @author: caoyj
 * @date: 2021/4/21
 **/
public class RequestResponseBodyMethodProcessorExt extends RequestResponseBodyMethodProcessor {

    @Value("${bosc.msb.validation.enable:true}")
    private volatile boolean isOpenValidation;

    private ConcurrentHashMap<Class<?>, ValidationTargetWrapper> validatorMap = new ConcurrentHashMap<>();

    public RequestResponseBodyMethodProcessorExt(List<HttpMessageConverter<?>> converters) {
        super(converters);
    }

    public RequestResponseBodyMethodProcessorExt(List<HttpMessageConverter<?>> converters, ContentNegotiationManager manager, List<Object> requestResponseBodyAdvice) {
        super(converters, manager, requestResponseBodyAdvice);
    }

    @Override
    protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        boolean checkValid = false;
        // 判断是否有配置 @Validated 或 @Valid 注解, 配置 则走 spring 的现有逻辑
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
                binder.validate(validationHints);
                checkValid = true;
                break;
            }
        }
        if( isOpenValidation ){
            // 如果开启了 平台级的参数校验 则 即使未 设置 @Validated 或 @Valid 注解也需要执行 参数校验
            // 此处判断是 执行 JSR 规范化的 参数校验, checkValid=false 代表 方法没有设置 校验注解
            if( !checkValid ){
                binder.validate(null);
                List<FieldError> feList = binder.getBindingResult().getFieldErrors();
            }
            // 执行 值对象 校验逻辑
            validatorMap.computeIfAbsent(binder.getTarget().getClass(),clazz-> new ValidationTargetWrapper(clazz))
                    .validate(binder,binder.getTarget());
        }
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBody.class) && parameter.getParameterType().isAnnotationPresent(ApiModel.class);
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        return (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBody.class) ||
                returnType.hasMethodAnnotation(ResponseBody.class));
    }
}
