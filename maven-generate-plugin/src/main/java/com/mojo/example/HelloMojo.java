package com.mojo.example;

import com.mojo.constant.ExcelConstant;
import com.mojo.utils.ExcelUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 18:28
 * @Description:测试自定义Mojo 方式二：@goal
 * @goal hello
 */
public class HelloMojo extends AbstractMojo {

    @Override
    public void execute() {

        getLog().info("========= HelloMojo ==========");
        //创建HSSFWorkbook对象
        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            getLog().info("进入导出模板方法");

            XSSFWorkbook workbook = ExcelUtil.generateXSSWorkbook();

            assert workbook != null;
            ExcelUtil.generateIndexProvider(workbook, ExcelConstant.INDEX_INDEX_P, 1, "交易码", "交易名称", "333", "444");
            ExcelUtil.generateIndexProvider(workbook, ExcelConstant.INDEX_INDEX_C, 1, "交易码", "交易名称", "333", "444");

            ExcelUtil.generateFilds(ExcelConstant.EXCEL_SERVICE_FILE_PATH);
            //输出Excel文件
            OutputStream output = new FileOutputStream(ExcelConstant.EXCEL_SERVICE_FILE);
            workbook.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            getLog().error("导出统计模板生成excel异常", e);
        }
        getLog().info("方法运行结束");
    }
}
