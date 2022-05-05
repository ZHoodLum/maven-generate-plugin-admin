package com.mojo.example;

import com.mojo.constant.ExcelConstant;
import com.mojo.utils.ExcelUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 18:28
 * @Description:测试自定义Mojo 方式二：@goal
 * @goal hello
 */
public class HelloMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoFailureException {
        getLog().info("========= HelloMojo ==========");
        //创建HSSFWorkbook对象
        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            getLog().info("进入导出模板方法");
            InputStream excelServiceFile = this.getClass().getClassLoader().getResourceAsStream(ExcelConstant.EXCEL_SERVICE_TEMPLATE);

            //获取模板文件路径
            String templatePath = this.getClass().getClassLoader().getResource(ExcelConstant.EXCEL_SERVICE_TEMPLATE).getPath();
            getLog().info("获取插件Excel模板文件位置" + excelServiceFile);

            XSSFWorkbook workbook = ExcelUtils.getExcelFile(excelServiceFile);

            XSSFSheet sheet = workbook.getSheet("INDEX_P");
            int sheetRowNum = 1;
            XSSFSheet exdSheet = workbook.getSheet("INDEX_C");
            int exdSheetRow = 1;
            for (int i = 0; i < 10; i++) {
                XSSFRow row = sheet.createRow(sheetRowNum);
            }

            //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
//            XSSFRow row1 = sheet.createRow(1);
            //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
//            XSSFCell cell = row1.createCell(0);
            //合并单元格
//            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
            //向合并单元格中输入字段
//            cell.setCellValue("客户支付流程跟踪导入和统计");
            //在sheet里创建第二行(下标为1)
            XSSFRow row2 = sheet.createRow(1);
            //创建单元格并设置单元格内容
            row2.createCell(0).setCellValue("渠道类型");
            row2.createCell(1).setCellValue("操作步骤");
            row2.createCell(2).setCellValue("操作总次数");
            row2.createCell(3).setCellValue("备用字段1");
            row2.createCell(4).setCellValue("备用字段2");
            row2.createCell(5).setCellValue("备用字段3");
            //输出Excel文件
//            OutputStream output = new FileOutputStream(ExcelConstant.EXCEL_SERVICE_FILE);
//            wb.write(output);
//            output.flush();
//            output.close()

        } catch (Exception e) {
            getLog().error("导出统计模板生成excel异常", e);
        }
        getLog().info("方法运行结束");
    }
}
