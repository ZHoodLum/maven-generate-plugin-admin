package com.swagger.validation;

import org.springframework.web.bind.WebDataBinder;
import com.swagger.annotation.ApiModelProperty;

import java.lang.annotation.Annotation;

/**
 * @className: ValidatorValueImpl
 * @description: 校验值处理器
 * @author: caoyj
 * @date: 2021/4/21
 **/
public class ValidatorApiModelPropertyImpl extends AbstractValidatorValue{

    @Override
    public void validateObject(Class target, Object fieldValue, String fieldName, Annotation annotation, WebDataBinder binder) {
        ApiModelProperty amp = (ApiModelProperty) annotation;
        if( fieldValue==null && (amp.required() || !amp.allowEmptyValue()) ){
            binder.getBindingResult().addError(addError(
                    target, String.format("类:[%s], 字段:[%s], 值:[null], 失败原因:[不能为null]",target.getSimpleName(),fieldName))
            );
        }
        if( amp!=null && fieldValue!=null ){
            String value = String.valueOf(fieldValue);
            if( value.length() < Integer.max(amp.min(),0) || value.length() > Long.min(amp.max(),Long.MAX_VALUE)  ){
                binder.getBindingResult().addError(addError(
                        target, String.format("类:[%s], 字段:[%s], 值:[%s], 失败原因:[个数必须在%s和%s之间]",
                                target.getSimpleName(),fieldName,fieldValue,amp.min(),amp.max()))
                );
            }
        }
    }

    @Override
    public Class<? extends Annotation> support() {
        return ApiModelProperty.class;
    }
}
