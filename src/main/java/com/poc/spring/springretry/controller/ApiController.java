package com.poc.spring.springretry.controller;

import com.poc.spring.springretry.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @Autowired
    private ApiService apiService;

    private int errorSimulator;

    @GetMapping(value = "/")
    public String testingRetry() {
        errorSimulator++;
        return apiService.callExternalService(errorSimulator);
    }
}
