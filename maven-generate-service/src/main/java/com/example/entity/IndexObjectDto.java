package com.example.entity;

import com.swagger.annotation.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/6/8 9:42
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(value="IndexObjectDto对象", description="")
public class IndexObjectDto {
    @ApiModelProperty(value = "交易编码",required = true, min = 1, max = 5)
    String id;
}
