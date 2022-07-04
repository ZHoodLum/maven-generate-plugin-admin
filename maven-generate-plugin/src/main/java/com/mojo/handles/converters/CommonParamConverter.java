package com.mojo.handles.converters;

import com.mojo.handles.ExcelHandle;
import com.mojo.handles.annotation.EntityParamType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/7/4 17:28
 * @Description: 普通字段处理逻辑
 */
@EntityParamType(sourceType = "common")
@Slf4j
public class CommonParamConverter implements ExcelHandle {

    @Override
    public void fillExcelRow() {
        log.info("普通字段处理逻辑");
    }
}
