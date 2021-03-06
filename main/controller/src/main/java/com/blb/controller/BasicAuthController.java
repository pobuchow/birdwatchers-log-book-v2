package com.blb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class BasicAuthController {

    @ResponseBody
    @GetMapping(path = "/basic")
    public HttpStatus authenticate() {
        return HttpStatus.OK;
    }
}
