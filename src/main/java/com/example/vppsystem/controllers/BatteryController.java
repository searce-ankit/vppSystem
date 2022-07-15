package com.example.vppsystem.controllers;


import com.example.vppsystem.dto.BatteryDto;
import com.example.vppsystem.dto.BatteryPostcodeRangeDto;
import com.example.vppsystem.mapper.BatteryMapper;
import com.example.vppsystem.model.Battery;
import com.example.vppsystem.services.BatteryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/batteries")
@AllArgsConstructor
public class BatteryController {

    @Autowired
    private final BatteryService batteryService;


    /**
    * GET API endpoint
    * Returns battery list along with total watt capacity and average watt capacity within range of postcodes.
    *
    * @param {long} Request Parameter for fromPostcode
    * @param {long} Request Parameter for toPostcode
    *
    */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<BatteryPostcodeRangeDto> getBatteriesInPostcodeRange(
            @RequestParam long fromPostcode,
            @RequestParam long toPostcode) {

        List<Battery> batteryList = batteryService.getBatteriesInPostcodeRange(fromPostcode, toPostcode);
        List<BatteryDto> batteries = batteryList.stream()
                .map(battery -> BatteryMapper.map(battery))
                .collect(Collectors.toList());

        double totalCapacityInWatt = batteryService.totalCapacityInWatt(batteryList);
        double averageCapacityInWatt = batteryService.averageCapacityInWatt(batteryList);

        BatteryPostcodeRangeDto response =
                new BatteryPostcodeRangeDto(batteries, totalCapacityInWatt, averageCapacityInWatt);

        return new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
    }


     /**
     * POST API endpoint
     * Saves batteries passed as request body and returns saved batteries.
     *
     * @param {List<BatteryDto>} Request Body Parameter for battery List.
     *
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<List<BatteryDto>> saveBatteries(@RequestBody List<BatteryDto> batteries) {
        List<Battery> batteryDataList = batteries.stream()
                .map(batteryDto -> BatteryMapper.map(batteryDto))
                .collect(Collectors.toList());

        List<Battery> createdBatteries = batteryService.save(batteryDataList);

        List<BatteryDto> createdBatteryResponse = createdBatteries.stream()
                .map(battery -> BatteryMapper.map(battery))
                .collect(Collectors.toList());

        return new ResponseEntity<>(createdBatteryResponse,new HttpHeaders(),HttpStatus.CREATED);
    }

}
