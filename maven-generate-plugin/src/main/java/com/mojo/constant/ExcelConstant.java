package com.mojo.constant;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/5 17:34
 * @Description: Excel全局静态类
 */
public class ExcelConstant {
    /**
     * 字段映射文件Excel模板路径
     */
    public static final String EXCEL_SERVICE_TEMPLATE = "excel_service_template.xlsx";

    /**
     * 输出的文件命名
     */
    public static final String EXCEL_SERVICE_FILE_PATH = "target" + File.separator + "excel";
    public static final String EXCEL_SERVICE_FILE = EXCEL_SERVICE_FILE_PATH + File.separator + "excel_service.xlsx";

    /**
     * Sheet页中参数配置
     */

    /**
     * sheet名称 INDEX_C
     */
    public static final String INDEX_INDEX_C = "INDEX_C";
    /**
     * sheet名称 INDEX_P
     */
    public static final String INDEX_INDEX_P = "INDEX_P";

    /**
     * 交易码
     */
    public static final Integer INDEX_TRANSACTION_CODE = 1;
    /**
     * 交易名称
     */
    public static final Integer INDEX_TRANSACTION_NAME = 2;
    /**
     * 消费方系统简称
     */
    public static final Integer INDEX_CONSUMER_NAME = 7;
    /**
     * 提供方系统简称
     */
    public static final Integer INDEX_PROVIDER_NAME = 8;

}
