package com.example.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/25 9:34
 * @Description:
 */
public class GeneratePrometheusInfo {
    public static void main(String[] args) throws IOException {

        File f = new File("D:\\IdeaProject\\maven-generate-plugin-admin\\maven-generate-service\\src\\main\\resources\\templates\\prometheus");

        String publicString = "# HELP jvm_gc_collection_seconds Time spent in a given JVM garbage collector in seconds.\n" +
                "jvm_gc_collection_seconds_count{gc=\"PS Scavenge\",} 117.0\n" +
                "jvm_gc_collection_seconds_sum{gc=\"PS Scavenge\",} 3.041\n" +
                "jvm_gc_collection_seconds_count{gc=\"PS MarkSweep\",} 4.0\n" +
                "jvm_gc_collection_seconds_sum{gc=\"PS MarkSweep\",} 0.965\n" +
                "jvm_buffer_count_buffers{application=\"UCFC.GATEWAY\",id=\"direct\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1119.0\n" +
                "jvm_buffer_count_buffers{application=\"UCFC.GATEWAY\",id=\"mapped\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "system_cpu_usage{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.11111111111111112\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Eden Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 7.08313088E8\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Compressed Class Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.1927552E7\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Old Gen\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.431830528E9\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Code Cache\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 5.0528256E7\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Survivor Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3670016.0\n" +
                "jvm_memory_committed_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Metaspace\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 9.1357184E7\n" +
                "process_start_time_seconds{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.65232644474E9\n" +
                "jetty_async_requests_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_buffer_memory_used_bytes{application=\"UCFC.GATEWAY\",id=\"direct\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 8709651.0\n" +
                "jvm_buffer_memory_used_bytes{application=\"UCFC.GATEWAY\",id=\"mapped\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_responses_total{application=\"UCFC.GATEWAY\",status=\"5xx\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_responses_total{application=\"UCFC.GATEWAY\",status=\"2xx\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_responses_total{application=\"UCFC.GATEWAY\",status=\"3xx\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_responses_total{application=\"UCFC.GATEWAY\",status=\"1xx\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_responses_total{application=\"UCFC.GATEWAY\",status=\"4xx\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_buffer_total_capacity_bytes{application=\"UCFC.GATEWAY\",id=\"direct\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 8709650.0\n" +
                "jvm_buffer_total_capacity_bytes{application=\"UCFC.GATEWAY\",id=\"mapped\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "hystrix_circuit_breaker_open{group=\"GATEWAY\",key=\"init\",} 0.0\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Eden Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3.08305744E8\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Compressed Class Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.1236048E7\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Old Gen\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 8.4496264E7\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Code Cache\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 4.9997568E7\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Survivor Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3244064.0\n" +
                "jvm_memory_used_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Metaspace\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 8.8096192E7\n" +
                "jetty_async_expires_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_classes_unloaded_classes_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 122.0\n" +
                "jetty_async_requests_waiting_max{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "jetty_dispatched_active_max{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Eden Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 7.08313088E8\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Compressed Class Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.073741824E9\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Old Gen\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.431830528E9\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Code Cache\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 2.5165824E8\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"heap\",id=\"PS Survivor Space\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3670016.0\n" +
                "jvm_memory_max_bytes{application=\"UCFC.GATEWAY\",area=\"nonheap\",id=\"Metaspace\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} -1.0\n" +
                "system_load_average_1m{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.1\n" +
                "queue_free_capacity{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"FramePools\",} 10000.0\n" +
                "queue_free_capacity{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"Priority_01_Pools\",} 1000.0\n" +
                "queue_free_capacity{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"PriorityPools\",} 50000.0\n" +
                "jetty_dispatched_seconds_count{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_dispatched_seconds_sum{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_requests_active{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "queue_task_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"FramePools\",} 0.0\n" +
                "queue_task_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"Priority_01_Pools\",} 0.0\n" +
                "queue_task_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"PriorityPools\",} 0.0\n" +
                "jetty_dispatched_active{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"FramePools\",} 500.0\n" +
                "pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"Priority_01_Pools\",} 0.0\n" +
                "pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"PriorityPools\",} 0.0\n" +
                "jetty_responses_size_bytes_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "total_error_code{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",error_code=\"MSB-GATEWAY-0010\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"mock-out\",system_name=\"SQ-UCFC-UAT1-4\",} 2.0\n" +
                "total_error_code{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",error_code=\"MSB-GATEWAY-0087\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"huigui-out\",system_name=\"SQ-UCFC-UAT1-4\",} 4.0\n" +
                "total_error_code{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",error_code=\"MSB-GATEWAY-0042\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"mock-out\",system_name=\"SQ-UCFC-UAT1-4\",} 0.0\n" +
                "total_error_code{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",error_code=\"MSB-GATEWAY-0010\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"TCP-out\",system_name=\"SQ-UCFC-UAT1-4\",} 3.0\n" +
                "maximum_pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"FramePools\",} 500.0\n" +
                "maximum_pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"Priority_01_Pools\",} 10.0\n" +
                "maximum_pool_size{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"PriorityPools\",} 20.0\n" +
                "jetty_async_requests_waiting{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "jetty_dispatched_time_max_seconds{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "active_thread_count{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"FramePools\",} 0.0\n" +
                "active_thread_count{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"Priority_01_Pools\",} 0.0\n" +
                "active_thread_count{application_name=\"UCFC.gateway\",thread_pool_type=\"inside\",thread_pool_name=\"PriorityPools\",} 0.0\n" +
                "jvm_gc_live_data_size_bytes{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 8.2604944E7\n" +
                "system_cpu_count{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 4.0\n" +
                "jvm_gc_memory_allocated_bytes_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 6.9843004448E10\n" +
                "jetty_requests_seconds_count{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_requests_seconds_sum{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_gc_memory_promoted_bytes_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 2.837308E7\n" +
                "hystrix_execution_total{event=\"short_circuited\",group=\"GATEWAY\",key=\"init\",} 0.0\n" +
                "jvm_classes_loaded_classes{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 16167.0\n" +
                "jetty_stats_seconds{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "process_uptime_seconds{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 94536.719\n" +
                "jvm_gc_max_data_size_bytes{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.431830528E9\n" +
                "jvm_gc_pause_seconds_count{action=\"end of major GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.0\n" +
                "jvm_gc_pause_seconds_sum{action=\"end of major GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.407\n" +
                "jvm_gc_pause_seconds_count{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 1.0\n" +
                "jvm_gc_pause_seconds_sum{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.026\n" +
                "jvm_gc_pause_seconds_count{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"Allocation Failure\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 102.0\n" +
                "jvm_gc_pause_seconds_sum{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"Allocation Failure\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 2.457\n" +
                "jvm_gc_pause_seconds_max{action=\"end of major GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_gc_pause_seconds_max{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"System.gc()\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jvm_gc_pause_seconds_max{action=\"end of minor GC\",application=\"UCFC.GATEWAY\",cause=\"Allocation Failure\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_async_dispatches_total{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n" +
                "jetty_request_time_max_seconds{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} NaN\n" +
                "process_cpu_usage{application=\"UCFC.GATEWAY\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0044444444444444444\n" +
                "transaction_failure_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 9.0\n" +
                "tps{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",provider_application_name=\"ALL\",provider_adapter_name=\"ALL\",system_name=\"ALL\",} 0.0\n" +
                "transaction_successful_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3.0\n" +
                "transaction_average_time{application_name=\"UCFC.gateway\",content_path=\"ALL\",consumer_application_name=\"ALL\",provider_application_name=\"ALL\",provider_adapter_name=\"ALL\",system_name=\"ALL\",} 0.0\n" +
                "transaction_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"ALL\",content_path=\"ALL\",provider_adapter_name=\"ALL\",provider_application_name=\"ALL\",system_name=\"ALL\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 12.0\n";

        String transactionTotal = "transaction_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/${bosc-msb-mock}/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 4.0\n";
        String transactionAverageTime = "transaction_average_time{application_name=\"UCFC.gateway\",content_path=\"/bosc-msb-mock/mock/generate\",consumer_application_name=\"Mock-In\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"MOCK-HTTP-OUT\",system_name=\"SQ-MOCK-UAT1-4\",} 0.0\n";
        String transactionSuccessfulTotal = "transaction_successful_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/bosc-msb-mock/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 0.0\n";
        String tps = "tps{application_name=\"UCFC.gateway\",content_path=\"/bosc-msb-mock/mock/generate\",consumer_application_name=\"Mock-In\",provider_application_name=\"UCFC.message-demo1\",provider_adapter_name=\"MOCK-HTTP-OUT\",system_name=\"SQ-MOCK-UAT1-4\",} 0.0\n";
        String transactionFailureTotal = "transaction_failure_total{application=\"UCFC.GATEWAY\",application_name=\"UCFC.gateway\",consumer_application_name=\"Mock-In\",content_path=\"/bosc-msb-mock/mock/generate\",provider_adapter_name=\"MOCK-HTTP-OUT\",provider_application_name=\"UCFC.message-demo1\",system_name=\"SQ-MOCK-UAT1-4\",type=\"gateway\",version=\"1.0.19-RELEASE\",zone=\"SQ\",} 3.0\n";

        List<String> stringList = new ArrayList<String>();
        Map<String, String> mapString = new HashMap<String, String>();
        mapString.put("transactionTotal", transactionTotal);
        mapString.put("transactionAverageTime", transactionAverageTime);
        mapString.put("transactionSuccessfulTotal", transactionSuccessfulTotal);
        mapString.put("tps", tps);
        mapString.put("transactionFailureTotal", transactionFailureTotal);

        FileWriter fw = null;
        int interfaceNum = 5000;
        try {
            fw = new FileWriter(f);
            FileWriter finalFw = fw;
            mapString.forEach((k, v) ->{
                for (int x = 1; x <= interfaceNum; x++) {
                    System.out.println(k + ": 生成接口第" + x + "指标");
                    try {
                        finalFw.write(v.replace("${bosc-msb-mock}", "gateway-mock-" + x));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert fw != null;
            fw.close();
        }
    }
}
