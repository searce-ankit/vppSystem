package com.example.vppsystem.repositories;

import com.example.vppsystem.model.Battery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class BatteryRepositoryTest {

    @Autowired
    private BatteryRepository batteryRepository;

    @Test
    void saveAll_success() {
        List<Battery> batteries = getTestBatteries();
        List<Battery> savedBatteries = batteryRepository.saveAll(batteries);

        assertEquals(3, savedBatteries.size());
    }

    @Test
    void findByPostcodeBetweenOrderByNameAsc_success(){
        List<Battery> batteries = getTestBatteries();
        batteryRepository.saveAll(batteries);

        int fromPostcode = 302000;
        int toPostcode = 402000;

        List<Battery> filteredBatteries = batteryRepository.findByPostcodeBetweenOrderByNameAsc(fromPostcode, toPostcode);

        assertEquals(2, filteredBatteries.size());
        assertEquals(1, filteredBatteries.get(0).getId());
        assertEquals(3, filteredBatteries.get(1).getId());
    }

    private List<Battery> getTestBatteries(){
        Battery battery1=new Battery(1l,"Battery 1",302033,100.0);
        Battery battery2=new Battery(2l,"Battery 2",402033,200.0);
        Battery battery3=new Battery(3l,"Battery 3",302040,300.0);

        List<Battery> batteries= Arrays.asList(battery1,battery2,battery3);

        return batteries;
    }
}
