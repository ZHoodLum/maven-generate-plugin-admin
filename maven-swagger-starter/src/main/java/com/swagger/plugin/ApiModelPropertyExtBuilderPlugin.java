package com.swagger.plugin;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Optional;
import com.swagger.annotation.ApiModelProperty;
import org.springframework.core.annotation.Order;
import springfox.documentation.schema.Annotations;
import springfox.documentation.service.AllowableRangeValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.schema.ModelPropertyBuilderPlugin;
import springfox.documentation.spi.schema.contexts.ModelPropertyContext;
import springfox.documentation.spring.web.DescriptionResolver;

/**
 * @className: ApiModelPropertyExtBuilderPlugin
 * @description: 扩展 @ApiModelProperty 注解, 给其添加 校验参数
 * @author: caoyj
 * @date: 2021/4/20
 **/
@Order(-2147482649)
public class ApiModelPropertyExtBuilderPlugin  implements ModelPropertyBuilderPlugin {
    private final DescriptionResolver descriptions;

    public ApiModelPropertyExtBuilderPlugin(DescriptionResolver descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void apply(ModelPropertyContext context) {
        Optional<ApiModelProperty> annotation = Optional.absent();
        if (context.getAnnotatedElement().isPresent()) {
            annotation = annotation.or(ApiModelProperties.findApiModePropertyAnnotation(context.getAnnotatedElement().get()));
        }

        if (context.getBeanPropertyDefinition().isPresent()) {
            annotation = annotation.or(Annotations.findPropertyAnnotation(context.getBeanPropertyDefinition().get(), ApiModelProperty.class));
        }

        if (annotation.isPresent()) {
            context.getBuilder().allowableValues(new AllowableRangeValues(annotation.get().min()+"",annotation.get().max()+"")).required((Boolean)annotation.transform(ApiModelProperties.toIsRequired()).or(false)).readOnly((Boolean)annotation.transform(ApiModelProperties.toIsReadOnly()).or(false)).description((String)annotation.transform(ApiModelProperties.toDescription(this.descriptions)).orNull()).isHidden((Boolean)annotation.transform(ApiModelProperties.toHidden()).or(false)).type((ResolvedType)annotation.transform(ApiModelProperties.toType(context.getResolver())).orNull()).position((Integer)annotation.transform(ApiModelProperties.toPosition()).or(0)).example((String)annotation.transform(ApiModelProperties.toExample()).orNull());
        }

    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
