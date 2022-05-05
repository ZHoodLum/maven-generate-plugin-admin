package com.mojo.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/5 17:32
 * @Description: excel文件导出工具类
 */
public class ExcelUtils {

    /**
     * 获取模板文件对象
     * @param excelServiceFile
     * @return
     */
    public static XSSFWorkbook getExcelFile(InputStream excelServiceFile) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(excelServiceFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }

    public static XSSFWorkbook getExcelFile(String templatePath) {
        File file = new File(templatePath);
        XSSFWorkbook xssfWorkbook = null;

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(templatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xssfWorkbook;
    }
}
