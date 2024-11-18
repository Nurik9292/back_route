package com.takykulgam.ugur_v2.adaters.controllers;

import com.takykulgam.ugur_v2.applications.usecase.input.FetchBusData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bus")
public class BusController {


    private final FetchBusData fetchBusData;

    @Autowired
    public BusController(FetchBusData fetchBusData) {
        this.fetchBusData = fetchBusData;
    }

    @GetMapping
    public ResponseEntity<String> bus() {
        fetchBusData.fetchBusData();
        return  ResponseEntity.ok("Bus Data Fetched");
    }
}
