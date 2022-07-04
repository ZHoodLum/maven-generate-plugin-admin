package com.mojo.handles.converters;

import com.mojo.handles.ExcelHandle;
import com.mojo.handles.annotation.EntityParamType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/7/4 17:27
 * @Description: 集合字段处理逻辑
 */
@EntityParamType(sourceType = "collection")
@Slf4j
public class CollectionParamConverter implements ExcelHandle {
    @Override
    public void fillExcelRow() {
        log.info("集合字段处理逻辑");
    }
}
