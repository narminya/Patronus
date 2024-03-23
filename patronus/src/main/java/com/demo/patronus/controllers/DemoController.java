package com.demo.patronus.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo")
@RequiredArgsConstructor
@Slf4j
public class DemoController {
    @GetMapping
    @ResponseBody
    public ResponseEntity get() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }


}