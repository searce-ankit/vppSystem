package com.example.vppsystem.mapper;

import com.example.vppsystem.dto.BatteryDto;
import com.example.vppsystem.model.Battery;

public class BatteryMapper {

    public static Battery map(BatteryDto dto){
       return Battery.builder()
                .name(dto.getName())
                .postcode(dto.getPostcode())
                .capacityInWatt(dto.getCapacityInWatt()).build();
    }

    public static BatteryDto map(Battery model){
        return new BatteryDto(model.getId(), model.getName(),model.getPostcode(),model.getCapacityInWatt());
    }
}
