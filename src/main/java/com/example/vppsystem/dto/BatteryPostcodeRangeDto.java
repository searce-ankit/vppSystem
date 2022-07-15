package com.example.vppsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BatteryPostcodeRangeDto {

    List<BatteryDto> batteries;
    double totalCapacity;
    double averageCapacity;
}
