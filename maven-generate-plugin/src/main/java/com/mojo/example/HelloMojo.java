package com.mojo.example;

import com.mojo.constant.ExcelConstant;
import com.mojo.utils.ExcelUtil;
import org.apache.maven.plugin.AbstractMojo;
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
 * @goal hello
 */
public class HelloMojo extends AbstractMojo {

    @Override
    public void execute() {
        getLog().info("========= HelloMojo ==========");
    }
}
