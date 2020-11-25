package com.qiuke.springcloud.controller;

import com.qiuke.springcloud.entities.CommonResult;
import com.qiuke.springcloud.entities.Payment;
import com.qiuke.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Slf4j
public class OrderController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> get(@PathVariable("id") Long id){
        return paymentFeignService.get(id);
    }

    @GetMapping("/consumer/payment/timeout")
    public String timeout(){
        return paymentFeignService.timeout();
    }
}
