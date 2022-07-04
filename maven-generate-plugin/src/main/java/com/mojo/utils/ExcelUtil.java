package com.mojo.utils;

import com.mojo.constant.ExcelConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugins.annotations.Component;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.Objects;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/5/5 17:32
 * @Description: excel文件导出工具类
 */
@Slf4j
public class ExcelUtil {

    /**
     * 创建XSSFWorkbook模板对象  2007版本Excel 后缀名为xlsx
     *
     * @return
     */
    public static XSSFWorkbook generateXSSWorkbook() {
        try {
            InputStream excelServiceFile = ExcelUtil.class.getClassLoader().getResourceAsStream(ExcelConstant.EXCEL_SERVICE_TEMPLATE);

            String templatePath;

            if (null == excelServiceFile) {
                templatePath = Objects.requireNonNull(ExcelUtil.class.getClassLoader().getResource(ExcelConstant.EXCEL_SERVICE_TEMPLATE)).getPath();
                log.info("获取maven插件中Excel-getPath模板文件位置: {}", templatePath);
                return ExcelUtil.getExcelFile(templatePath);
            } else {
                log.info("获取maven插件中Excel-Input模板文件位置: {}", excelServiceFile);
                return ExcelUtil.getExcelFile(excelServiceFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("获取maven插件中Excel模板文件位置异常, 在maven命令中后面加 -X 使用DEBUG输入日志查看详情");
        }
        return null;
    }

    /**
     * 根据 InputStream 获取模板文件对象
     * 适用于【拿取 jar 包内】的模板文件
     *
     * @param excelServiceFile InputStream对象
     * @return XSSFWorkbook
     */
    public static XSSFWorkbook getExcelFile(InputStream excelServiceFile) {
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(excelServiceFile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("根据 InputStream 获取模板文件对象, 在maven命令中后面加 -X 使用DEBUG输入日志查看详情");
        }
        return xssfWorkbook;
    }

    /**
     * 根据 templatePath 获取模板文件对象
     * 适用于【拿取 jar 包外】的模板文件
     *
     * @param templatePath 文件路径
     * @return XSSFWorkbook
     */
    public static XSSFWorkbook getExcelFile(String templatePath) {
        File file = new File(templatePath);
        XSSFWorkbook xssfWorkbook = null;

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(file);
            xssfWorkbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("根据 templatePath 获取模板文件对象, 在maven命令中后面加 -X 使用DEBUG输入日志查看详情");
        }
        return xssfWorkbook;
    }

    public static void fillExcelInfo(XSSFWorkbook workbook) {
        for (int i = 0; i < 10; i++) {
            //是否为数组
            if (ExcelConstant.MAPPING_SET.contains("List")) {
                //处理带有数组的sheet页信息
                ExcelUtil.generateIndexMapping(workbook, ExcelConstant.INDEX_INDEX_C, 1, "交易码", "交易名称", "333", "444");
            }else{
                //处理普通sheet页
                ExcelUtil.generateIndexProvider(workbook, ExcelConstant.INDEX_INDEX_P, 1, "交易码", "交易名称", "333", "444");
                ExcelUtil.generateIndexProvider(workbook, ExcelConstant.INDEX_INDEX_C, 1, "交易码", "交易名称", "333", "444");
            }
        }
    }

    /**
     * 填充sheet页 INDEX_P的内容
     *
     * @param workbook  XSSFWorkbook对象
     * @param numberRow 填充哪行 numberRow > 0
     * @param tCode     交易编码
     * @param tName     交易名称
     * @param conName   消费方系统英文名
     * @param proName   提供方系统英文名
     */
    public static void generateIndexProvider(XSSFWorkbook workbook, String sheetName, Integer numberRow, String tCode, String tName, String conName, String proName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);

        XSSFRow xssfRow = sheet.createRow(numberRow);
        generateCellStyle(workbook);
        //创建单元格并设置单元格内容
        xssfRow.createCell(ExcelConstant.INDEX_TRANSACTION_CODE).setCellValue(tCode);
        xssfRow.createCell(ExcelConstant.INDEX_TRANSACTION_NAME).setCellValue(tName);
        xssfRow.createCell(ExcelConstant.INDEX_CONSUMER_NAME).setCellValue(conName);
        xssfRow.createCell(ExcelConstant.INDEX_PROVIDER_NAME).setCellValue(proName);
        log.debug("sheet名称: {}  填充行数: {}  交易码:{}  交易名称:{}  消费方系统简称:{}  提供方系统简称: {}", sheetName, numberRow, tCode, tName, conName, proName);
    }

    public static void generateIndexMapping(XSSFWorkbook workbook, String sheetName, Integer numberRow, String tCode, String tName, String conName, String proName) {
        XSSFSheet sheet = workbook.getSheet(sheetName);

        XSSFRow xssfRow = sheet.createRow(numberRow);
        generateCellStyle(workbook);
        //创建单元格并设置单元格内容
        xssfRow.createCell(ExcelConstant.INDEX_TRANSACTION_CODE).setCellValue(tCode);
        xssfRow.createCell(ExcelConstant.INDEX_TRANSACTION_NAME).setCellValue(tName);
        xssfRow.createCell(ExcelConstant.INDEX_CONSUMER_NAME).setCellValue(conName);
        xssfRow.createCell(ExcelConstant.INDEX_PROVIDER_NAME).setCellValue(proName);
        log.debug("sheet名称: {}  填充行数: {}  交易码:{}  交易名称:{}  消费方系统简称:{}  提供方系统简称: {}", sheetName, numberRow, tCode, tName, conName, proName);
    }

    /**
     * 生成文件夹 用于存放生而成出来的excel文档
     *
     * @param filePath 生成出来的excel文档存放 路径
     */
    public static void generateFilds(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.mkdirs()) {
                    log.debug("生成文件夹成功: {}", filePath);
                } else {
                    log.debug("文件夹: {} 已存在, 不需要重新生成", filePath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("生成文件夹失败: {}", filePath);
        }
    }


    /**
     * 设置单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle generateCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//            cellStyle.setFillForegroundColor(new XSSFColor(Color.YELLOW));
//            cellStyle.setFillForegroundColor(IndexedColors.BLACK1.getIndex());
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
//            cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(1, 0, 0)));
        //生成一个字体
        XSSFFont font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 14);
        cellStyle.setFont(font);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 拷贝sheet页
     *
     * @param workbook     workbook对象
     * @param sheetName    复制后的sheet页新名称
     * @param delSheetFlag 是否删除原始的sheet页
     * @return
     */
    public Sheet copySheet(XSSFWorkbook workbook, String sheetName, boolean delSheetFlag) {
        XSSFSheet sheetInfo = workbook.getSheet("mappingSheetName");
        //复制模板中head页
        XSSFSheet sheet = workbook.cloneSheet(workbook.getSheetIndex(sheetInfo));
        //修改sheet名称
        workbook.setSheetName(workbook.getSheetIndex(sheet), sheetName);
        if (delSheetFlag) {
            //删除mapping页
            workbook.removeSheetAt(workbook.getSheetIndex(sheetInfo));
        }
        return sheet;
    }
}
