package com.example.controller;

import com.example.util.LogAPI;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/11 10:34
 * @Description:
 */
@RestController
@Slf4j
@Api(tags = "测试HelloWordController")
public class HelloWordController {

    public List<String> hello(String req) {
        log.info("Hello");
        return null;
    }

    public static void main(String[] args) {
        System.out.println(20 * 60 * 1000L);
        //HTTP下游 http://10.240.178.128:30016/test
        //TCP下游 Address{10.240.178.113：31017, 权重=1 连续失败次数=0 }
        String str1 = "http://10.240.178.128:30016/test";
        String str2 = "Address{10.240.178.113：31017, 权重=1 连续失败次数=0 }";
        System.out.println(str1.split(":")[1].split("//")[1]);
        System.out.println(str2.split("\\{")[1].split("：")[0]);
    }

    @LogAPI(code = "logApiMain")
    public String logApiMain() {
        log.info("使用注解logApiMain");
        return "使用注解logApiMain";
    }
}
