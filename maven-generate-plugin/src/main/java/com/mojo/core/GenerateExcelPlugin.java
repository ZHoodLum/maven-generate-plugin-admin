package com.mojo.core;

import com.mojo.constant.ExcelConstant;
import com.mojo.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.awt.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/4/21 18:28
 * @Description:测试自定义Mojo 方式二：@goal
 */
@Mojo(name = "genExcel", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
@Slf4j
public class GenerateExcelPlugin extends AbstractMojo {

    @Override
    public void execute() {

        getLog().info("========= HelloMojo ==========");
        //创建HSSFWorkbook对象
        try (HSSFWorkbook wb = new HSSFWorkbook()) {
            getLog().info("进入导出模板方法");
            //获取excel模板
            XSSFWorkbook workbook = ExcelUtil.generateXSSWorkbook();

            assert workbook != null;

            //填充excel内容
            ExcelUtil.fillExcelInfo(workbook);

            //生成文件夹及文件
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
