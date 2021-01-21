package cn.iot.controller;

import cn.iot.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc:
 * @Author: Penstaro
 * @Date: 2020/2/10 17:03
 */
@RestController
public class SendMessageController {

    @Autowired
    private ProducerService producerService;

    @GetMapping("/send")
    public ResponseEntity<String> sendMessage(){
        producerService.producerNumber();
        return ResponseEntity.ok("success");
    }
}
