package com.swagger.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.swagger.plugin.ApiModelPropertyExtBuilderPlugin;
import com.swagger.processor.RequestResponseBodyMethodProcessorExt;
import com.swagger.validation.ValidatorApiModelPropertyImpl;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.bean.validators.plugins.parameter.*;
import springfox.bean.validators.plugins.schema.DecimalMinMaxAnnotationPlugin;
import springfox.documentation.spring.web.DescriptionResolver;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: SwaggerValidationConfiguration
 * @description: 自定义的 swagger 校验启动器
 * @author: caoyj
 * @author wangxiaoquan
 * @date: 2021/4/20
 **/
@Configuration
@ConditionalOnClass(BeanValidatorPluginsConfiguration.class)
@AutoConfigureAfter(BeanValidatorPluginsConfiguration.class)
@Slf4j
public class SwaggerValidationConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public ExpandedParameterMinMaxAnnotationPlugin expanderMinMax() {
        return new ExpandedParameterMinMaxAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpandedParameterNotNullAnnotationPlugin expanderNotNull() {
        return new ExpandedParameterNotNullAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpandedParameterPatternAnnotationPlugin expanderPattern() {
        return new ExpandedParameterPatternAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public ExpandedParameterSizeAnnotationPlugin expanderSize() {
        return new ExpandedParameterSizeAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public MinMaxAnnotationPlugin parameterMinMax() {
        return new MinMaxAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public NotNullAnnotationPlugin parameterNotNull() {
        return new NotNullAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public PatternAnnotationPlugin parameterPattern() {
        return new PatternAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public SizeAnnotationPlugin parameterSize() {
        return new SizeAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public MinMaxAnnotationPlugin minMaxPlugin() {
        return new MinMaxAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public DecimalMinMaxAnnotationPlugin decimalMinMaxPlugin() {
        return new DecimalMinMaxAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public SizeAnnotationPlugin sizePlugin() {
        return new SizeAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public NotNullAnnotationPlugin notNullPlugin() {
        return new NotNullAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public PatternAnnotationPlugin patternPlugin() {
        return new PatternAnnotationPlugin();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiModelPropertyExtBuilderPlugin apiModelPropertyExtBuilderPlugin(@Autowired DescriptionResolver descriptions){
        return new ApiModelPropertyExtBuilderPlugin(descriptions);
    }

    @Bean
    @DependsOn("requestMappingHandlerAdapter")
    @ConditionalOnMissingBean
    public RequestResponseBodyMethodProcessorExt requestResponseBodyMethodProcessorExt(@Autowired RequestMappingHandlerAdapter adapter){
        RequestResponseBodyMethodProcessorExt ext = null;
        try{
            Field adviceField = ReflectionUtils.findField(RequestMappingHandlerAdapter.class,"requestResponseBodyAdvice");
            adviceField.setAccessible(true);
            List<Object> adviceFieldValue = (List<Object>) adviceField.get(adapter);
            ext = new RequestResponseBodyMethodProcessorExt(adapter.getMessageConverters(),null,adviceFieldValue);
        }catch(Exception e){
            log.warn("msb-frame|Get requestResponseBodyAdvice failed! ", e);
            ext =  new RequestResponseBodyMethodProcessorExt(adapter.getMessageConverters());
        }
        List<HandlerMethodArgumentResolver> resolvers = adapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> all = new ArrayList<>();
        // 获取 所有现有 已注入的 HandlerMethodArgumentResolver 列表
        all.addAll(resolvers);
        // 将 自定义的 放置 在前面，优先执行 处理
        all.add(0,ext);
        adapter.setArgumentResolvers(all);
        return ext;
    }

    @Bean
    @ConditionalOnMissingBean
    public ValidatorApiModelPropertyImpl validatorApiModelProperty(){
        return new ValidatorApiModelPropertyImpl();
    }
}
