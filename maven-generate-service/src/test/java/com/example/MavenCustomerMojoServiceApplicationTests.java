package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

@SpringBootTest
class MavenCustomerMojoServiceApplicationTests {

    @Test
    void contextLoads() {

        Long time = System.currentTimeMillis();  //获取当前时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = format.format(time);//注意这里返回的是string类型
        String date2 = format.format(time-10);//注意这里返回的是string类型
        System.out.println(date);
        System.out.println(date2);

    }

}
