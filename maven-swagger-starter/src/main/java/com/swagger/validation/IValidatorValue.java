package com.swagger.validation;

import org.springframework.web.bind.WebDataBinder;

import java.lang.annotation.Annotation;

/**
 * @className: IValidatorValue
 * @description: 值校验
 * @author: caoyj
 * @date: 2021/4/21
 **/
public interface IValidatorValue {
    /**
     * 参数校验 函数
     * @param value
     * @return
     */
    void validate(ValidatorTarget value, WebDataBinder binder, Object validateValue);

    /**
     * 支持 校验的 注解对象
     * @return
     */
    Class<? extends Annotation> support();
}
