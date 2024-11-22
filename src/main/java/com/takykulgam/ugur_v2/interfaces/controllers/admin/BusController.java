package com.takykulgam.ugur_v2.interfaces.controllers.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus")
public class BusController {

    @GetMapping
    public ResponseEntity<String> bus() {
        return  ResponseEntity.ok("Bus Data Fetched");
    }
}
