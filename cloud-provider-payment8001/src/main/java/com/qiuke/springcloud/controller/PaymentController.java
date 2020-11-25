package com.qiuke.springcloud.controller;

import com.qiuke.springcloud.entities.CommonResult;
import com.qiuke.springcloud.entities.Payment;
import com.qiuke.springcloud.service.PaymentService;
import io.micrometer.core.instrument.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {

        int resultRows = paymentService.create(payment);
        log.info("******插入结果:" + resultRows);

        if (resultRows > 0) {
            return new CommonResult(200, "插入数据库成功,serverPort:"+serverPort, resultRows);
        } else {
            return new CommonResult(444, "插入数据库失败");
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult get(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("******查询结果:" + payment);
        if (payment != null) {
            return new CommonResult(200, "查询数据库成功,serverPort:"+serverPort, payment);
        } else {
            return new CommonResult(444, "查询数据库失败" + id);
        }
    }

    @GetMapping(value = "/payment/discovery")
    public Object discovery() {
        // 得到服务信息
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*****element:"+service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info("*****id:"+instance.getServiceId());
            log.info("*****host:"+instance.getHost());
            log.info("*****port:"+instance.getPort());
            log.info("*****uri:"+instance.getUri());
        }

        return discoveryClient;
    }

    @GetMapping("/payment/timeout")
    public String timeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
