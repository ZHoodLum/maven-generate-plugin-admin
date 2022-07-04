package com.example.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@Configuration
@EnableSwagger2   //开启swagger注解
public class SwaggerConfig {

     @Bean  //配置swagger Docker bean实例
    public Docket docket(Environment environment){
        /*
            .slect().apis(RequestHandlerSelectors.basePackage("com.thxy.tygl.controller"))  扫描指定包下的接口
            .path(PathSelectors.ant("/*"))  指定符合条件的url地址接口

         */
        Profiles profile= Profiles.of("dev");     //创建开启swagger所需环境
        boolean flag=environment.acceptsProfiles(profile);   //检查环境是否是dev
//        System.out.println(flag);
        return  new Docket(DocumentationType.SWAGGER_2)
                .groupName("silence")   //配置分组信息
               .enable(flag)   //根据当前环境确定是否开启swagger
                .apiInfo(apiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.thxy.tygl.controller")).paths(PathSelectors.any()).build()
                ;
    }

    //配置Docker的Apiinfo信息
    public ApiInfo apiInfo(){
        Contact contact=new Contact("slience","https://mp.csdn.net/console/article","1195279222@qq.com");//作者信息
        return  new ApiInfo("Silence Api Documentation",   //首页标题
                            "tygl Api Documentation",   //首页描述
                            "1.0",            //版本
                            "https://mp.csdn.net/console/article",  //团队服务器URL
                            contact ,
                            "apache2.0",
                            "",
                            new ArrayList());
    }


}

