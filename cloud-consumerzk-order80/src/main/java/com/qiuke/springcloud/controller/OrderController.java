package com.qiuke.springcloud.controller;

import com.qiuke.springcloud.entities.CommonResult;
import com.qiuke.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    private static final String PAYMENT_URL = "http://cloud-payment-service";
    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/zk")
    public String zk(){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zk", String.class);
    }
}
