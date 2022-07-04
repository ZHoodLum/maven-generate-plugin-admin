package com.example.controller;

import com.example.entity.MessageDTO;
import com.swagger.vo.R;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @author：Psyduckzzzz
 * @Date：Created on 2022/6/8 9:27
 * @Description:
 */

@RestController
@Slf4j
@Api(value="/user", description = "赛事安排 Operations about user")
@RequestMapping("/fight/admin/fight-user")
public class GenerateExcelController {

    @ApiOperation(value = "赛事安排-搜索接口", notes = " \n author: liuqichun 2021/3/28")
    // ApiImplicitParams可以包含多个ApiImplicitParam
    // competitionId是路径参数，paramType为path，同时方法入参应使用@PathVariable表示，否则接收不到
    // criteria 和 pageVO 是查询的字段DTO类，又因为他们都是GET方法，因此paramType=query
    @ApiImplicitParams({
            @ApiImplicitParam(name = "competitionId", value = "赛事id", paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "criteria", value = "标准查询字段", paramType = "query", dataType = "CompetitionScheduleQueryCriteria")
    })
    @GetMapping("/{competitionId}/search")
    @PreAuthorize("@smpe.check()")
    public R<String> competitionScheduleSearch(@PathVariable Long competitionId, MessageDTO messageDTO) {
        log.info("创建对象");
        return R.newSuccess(messageDTO.toString());
    }

    @GetMapping("/{competitionId}/deleteUser")
    @ApiOperation(value = "Delete user",
            notes = "This can only be done by the logged in user.",
            position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid username supplied"),
            @ApiResponse(code = 404, message = "User not found") })
    public R<String> deleteUser(
            @ApiParam(value = "The name that needs to be deleted", required = true) @PathParam("messageDTO") String messageDTO) {
        log.info("创建对象");
        return R.newSuccess(messageDTO.toString());
    }

}
