package com.liunian.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MasterController {

    @GetMapping("/")
    public String root() {
        return "Hello Wechat!";
    }

}
