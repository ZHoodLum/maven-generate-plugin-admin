package com.example.entity;

import com.swagger.annotation.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/6/8 9:28
 * @Description:
 */
@Data
@Accessors(chain = true)
@ApiModel(value="MessageDTO对象", description="")
public class MessageDTO {

    // 代表 服务编码 必输, 并且最小长度：1位,最大长度: 5位
    @ApiModelProperty(value = "服务编码",required = true, min = 1, max = 5)
    String id;

    // 代表 服务编码 必输, 并且最小长度：1位,最大长度: 10位
    @ApiModelProperty(value = "服务名称",required = true, min = 1, max = 10)
    String name;

    // 代表 服务密钥 必输, 并且最大长度：18位, default模式下 mock 取值： example中的值
    @ApiModelProperty(value = "服务密钥",required = true,example = "secret123456", max = 18)
    String message;

    // 这个实体类对象,如果 内部有字段 标记了 @ApiModelProperty 那么当 indexObjectDto 不为Null时,也将进行参数验证
    private IndexObjectDto indexObjectDto;

    // 这些参数 将不参与到 参数校验中来
    private String attr1;
}
