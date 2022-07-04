package com.swagger.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="AuthClient对象", description="")
//public class AuthClientDtoTest implements Bosfx3Vo {
public class AuthClientDtoTest{

//    private static final long serialVersionUID=1L;
//
//    @NotNull(message = "请求记录id不允许为空")
//    @ApiModelProperty(value = "主键Id",required = true, max = 32)
//    @Max(32)
    private Integer id;
//
//    @ApiModelProperty(value = "服务编码",required = true, min = 4, max = 6)
//    @Size(min = 4,max = 6)
    private String code;
}
