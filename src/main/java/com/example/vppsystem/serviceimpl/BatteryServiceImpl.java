package com.example.vppsystem.serviceimpl;

import com.example.vppsystem.model.Battery;
import com.example.vppsystem.repositories.BatteryRepository;
import com.example.vppsystem.services.BatteryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BatteryServiceImpl implements BatteryService {

    private BatteryRepository batteryRepository;

    public List<Battery> save(List<Battery> batteries) {
        return batteryRepository.saveAll(batteries);
    }

    public List<Battery> getBatteriesInPostcodeRange(long fromPostcode, long toPostcode) {
        return batteryRepository.findByPostcodeBetweenOrderByNameAsc(fromPostcode, toPostcode);
    }

    public double totalCapacityInWatt(List<Battery> batteries) {
        return batteries.stream()
                .map(Battery::getCapacityInWatt)
                .reduce(0.0, Double::sum);
    }

    public double averageCapacityInWatt(List<Battery> batteries) {
        return batteries.stream()
                .map(Battery::getCapacityInWatt)
                .collect(Collectors.summarizingDouble(Double::doubleValue))
                .getAverage();
    }

}
