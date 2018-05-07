package com.poc.spring.springretry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    @Retryable(value = {Exception.class}, maxAttempts = 5)
    public String callExternalService(int errorSimulator) {
        System.out.println("Calling API");

        RestTemplate restTemplate = new RestTemplate();
        try {
            if ((errorSimulator & 1) == 0) {
                restTemplate.getForObject("https://www.google.com/error", String.class);
            } else {
                restTemplate.getForObject("https://www.google.com", String.class);
            }

        } catch (Exception e) {
            System.out.println("Catch Exception");
            System.out.println(">>>>>>" + e.getClass());
            throw e;
        }

        return "Success";
    }

    @Recover
    public String recover(HttpClientErrorException exception) {
        System.out.println("Calling HttpClientErrorException recover");
        return "HttpClientErrorException recover";
    }

    @Recover
    public String recover(Exception exception) {
        System.out.println("Calling Exception recover");
        return "Exception recover";
    }

}
