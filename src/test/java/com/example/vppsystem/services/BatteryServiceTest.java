package com.example.vppsystem.services;

import com.example.vppsystem.model.Battery;
import com.example.vppsystem.repositories.BatteryRepository;
import com.example.vppsystem.serviceimpl.BatteryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

public class BatteryServiceTest {
    @Mock
    private BatteryRepository batteryRepository;
    private BatteryService batteryService;

    private AutoCloseable autoCloseable;

    @BeforeEach
    void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        batteryService = new BatteryServiceImpl(batteryRepository);
    }

    @AfterEach
    void teardown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void getBatteriesInPostcodeRange_success() {
        int fromPostcode = anyInt();
        int toPostcode = anyInt();

        batteryService.getBatteriesInPostcodeRange(fromPostcode, toPostcode);

        verify(batteryRepository).findByPostcodeBetweenOrderByNameAsc(fromPostcode, toPostcode);
    }

    @Test
    void testTotalCapacityInWatt_success() {
        List<Battery> batteries = getTestBatteries();

        double totalCapacityInWatt = batteryService.totalCapacityInWatt(batteries);

        assertEquals(600, totalCapacityInWatt);
    }

    @Test
    void testTotalCapacityInWatt_WithEmptyBatteryList_returnsZero() {
        List<Battery> batteries = new ArrayList<>();

        double totalCapacityInWatt = batteryService.totalCapacityInWatt(batteries);

        assertEquals(0, totalCapacityInWatt);
    }

    @Test
    void testAverageCapacityInWatt_success() {
        List<Battery> batteries = getTestBatteries();

        double averageCapacityInWatt = batteryService.averageCapacityInWatt(batteries);

        assertEquals(200.0, averageCapacityInWatt);
    }

    @Test
    void testAverageCapacityInWatt_WithEmptyBatteryList_returnsZero() {
        List<Battery> batteries = new ArrayList<>();

        double averageCapacityInWatt = batteryService.averageCapacityInWatt(batteries);

        assertEquals(0.0, averageCapacityInWatt);
    }

    @Test
    void saveBatteries_success() {
        List<Battery> batteries = getTestBatteries();

        batteryService.save(batteries);

        verify(batteryRepository).saveAll(batteries);
    }

    private List<Battery> getTestBatteries(){
        Battery battery1=new Battery(1l,"Battery 1",302033,100.0);
        Battery battery2=new Battery(2l,"Battery 2",302040,200.0);
        Battery battery3=new Battery(3l,"Battery 3",402033,300.0);

        List<Battery> batteries= Arrays.asList(battery1,battery2,battery3);

        return batteries;
    }
}
