package com.sung.housingfinance.controller;

import com.sung.housingfinance.dto.response.ResponseData;
import com.sung.housingfinance.dto.response.ResponseDataFor1st;
import com.sung.housingfinance.kafka.ExampleKafkaProducer;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.print.DocFlavor;
import java.util.Map;

/*
 *
 * @author 123msn
 * @since 2019-07-18
 */
@RestController
@RequestMapping(value = "api/kafka")
public class MessageController {
    @PostMapping("")
    @ApiOperation(value ="카프카 메세지 생성 테스트")
    public ResponseData kafkaProducer(@RequestBody Map<String, String> request){

        ResponseData response = ExampleKafkaProducer.producer(request.get("message"));

        return response;
    }
}
