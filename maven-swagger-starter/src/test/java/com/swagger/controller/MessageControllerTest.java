package com.swagger.controller;

import com.swagger.entity.AuthClientDtoTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messageClient")
@Slf4j
public class MessageControllerTest {

    @PostMapping("/authClient")
    AuthClientDtoTest edit(@RequestBody AuthClientDtoTest authClient) {
        log.info("校验对象 {}", authClient);
        return authClient;
    }
}
