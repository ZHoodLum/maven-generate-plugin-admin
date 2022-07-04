package com.swagger.processor;

import com.swagger.configuration.SwaggerValidationConfiguration;
import com.swagger.entity.AuthClientDtoTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swagger.controller.MessageControllerTest;
import com.swagger.plugin.ApiModelPropertyExtBuilderPlugin;
import com.swagger.utils.BoscApplicationContextUtil;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = {MessageControllerTest.class, SwaggerValidationConfiguration.class, BoscApplicationContextUtil.class})
@EnableAutoConfiguration
@EnableWebMvc
class RequestResponseBodyMethodProcessorTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    MessageControllerTest messageController;
    @MockBean
    ApiModelPropertyExtBuilderPlugin apiModelPropertyExtBuilderPlugin;

    @Test
    void bigDataControllerAspectTest() throws Exception {
        log.info("测试请求参数校验方法-开始执行方法--/messageClient/authClient");
        AuthClientDtoTest authClientDto = new AuthClientDtoTest();
        authClientDto.setId(0);
        authClientDto.setCode("code");
        String str = new ObjectMapper().writeValueAsString(authClientDto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/messageClient/authClient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(str)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("code"))
                .andReturn();

    }
}
