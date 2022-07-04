package com.swagger.annotation;

import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @className: ApiModelProperty
 * @description:
 * @author: caoyj
 * @date: 2021/4/20
 **/
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModelProperty {
    String value() default "";

    String name() default "";

    String allowableValues() default "";

    String access() default "";

    String notes() default "";

    String dataType() default "";

    int min() default 0;

    int max();

    boolean required() default false;

    int position() default 0;

    boolean hidden() default false;

    String example() default "";

    /** @deprecated */
    @Deprecated
    boolean readOnly() default false;

    ApiModelProperty.AccessMode accessMode() default ApiModelProperty.AccessMode.AUTO;

    String reference() default "";

    boolean allowEmptyValue() default true;

    Extension[] extensions() default {@Extension(
            properties = {@ExtensionProperty(
                    name = "",
                    value = ""
            )}
    )};

    public static enum AccessMode {
        AUTO,
        READ_ONLY,
        READ_WRITE;

        private AccessMode() {
        }
    }
}
