package com.example.controller;

import com.example.util.LogAPI;
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
public class HelloWordController {

    public List<String> hello(String req) {
        log.info("Hello");
        return null;
    }

    public static void main(String[] args) {
        System.out.println(20 * 60 * 1000L);

    }

    @LogAPI(code = "logApiMain")
    public static void main2(String[] args) throws Exception {
        File f = new File("D:\\IdeaProject\\maven-generate-plugin-admin\\maven-generate-service\\src\\main\\resources\\templates\\prometheus");

        String transaction_total = "# HELP transaction_total\n" +
                "# TYPE transaction_total gauge\n" +
                "transaction_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 12.0\n" +
                "transaction_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/${bosc-msb-mock}/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 4.0\n";

        String transaction_average_time = "# HELP transaction_average_time transaction_average_time\n" +
                "# TYPE transaction_average_time gauge\n" +
                "transaction_average_time{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",provider_application_name=\"ALL\",provider_adapter_name=\"ALL\",system_name=\"ALL\",} 0.0\n" +
                "transaction_average_time{application_name=\"UCFC.gateway\",content_path=\"/bosc-msb-mock/mock/generate\",consumer_application_name=\"Mock-In\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"MOCK-HTTP-OUT\",system_name=\"SQ-MOCK-UAT1-4\",} 0.0";

        String transaction_successful_total = "# HELP transaction_successful_total transaction_successful_total_old\n" +
                "# TYPE transaction_successful_total gauge\n" +
                "# HELP transaction_successful_total  \n" +
                "# TYPE transaction_successful_total gauge\n" +
                "transaction_successful_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3.0\n" +
                "transaction_successful_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/bosc-msb-mock/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0";

        String tps = "# HELP tps tps\n" +
                "# TYPE tps gauge\n" +
                "tps{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",provider_application_name=\"ALL\",provider_adapter_name=\"ALL\",system_name=\"ALL\",} 0.0\n" +
                "tps{application_name=\"UCFC.gateway\",content_path=\"/bosc-msb-mock/mock/generate\",consumer_application_name=\"Mock-In\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"MOCK-HTTP-OUT\",system_name=\"SQ-MOCK-UAT1-4\",} 0.0";

        String transaction_failure_total = "# HELP transaction_failure_total  \n" +
                "# TYPE transaction_failure_total gauge\n" +
                "transaction_failure_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 9.0\n" +
                "transaction_failure_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/bosc-msb-mock/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3.0";

        List<String> stringList = new ArrayList<String>();
        stringList.add(transaction_total);
        stringList.add(transaction_average_time);
        stringList.add(transaction_successful_total);
        stringList.add(tps);
        stringList.add(transaction_failure_total);

        FileWriter fw = null;
        try {
            fw = new FileWriter(f);
            for (int x = 1; x <= 5000; x++) {
                System.out.println("生成接口指标: " + x);
                for (int i = 0; i < stringList.size(); i++) {
                    fw.write(transaction_total.replace("${bosc-msb-mock}", "gateway-mock-" + x));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert fw != null;
            fw.close();
        }
    }
}
