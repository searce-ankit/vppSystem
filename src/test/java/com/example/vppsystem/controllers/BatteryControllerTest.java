package com.example.vppsystem.controllers;

import com.example.vppsystem.dto.BatteryDto;
import com.example.vppsystem.mapper.BatteryMapper;
import com.example.vppsystem.model.Battery;
import com.example.vppsystem.services.BatteryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatteryController.class)
public class BatteryControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    BatteryService batteryService;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getAllBatteriesInPostcodeRange_success() throws Exception {
        int fromPostcode = 302020;
        int toPostcode = 403020;

        List<Battery> batteries = getTestBatteries();

        Mockito.when(batteryService.getBatteriesInPostcodeRange(fromPostcode, toPostcode)).thenReturn(batteries);
        Mockito.when(batteryService.totalCapacityInWatt(batteries)).thenReturn(600.0);
        Mockito.when(batteryService.averageCapacityInWatt(batteries)).thenReturn(200.0);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/batteries")
                .param("fromPostcode", Long.toString(fromPostcode))
                .param("toPostcode", Long.toString(toPostcode))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.batteries", hasSize(3)))
                .andExpect(jsonPath("$.totalCapacity").value(600.0))
                .andExpect(jsonPath("$.averageCapacity").value(200.0))
                .andReturn();
    }

    @Test
    void getAllBatteriesInPostcodeRange_emptyResponse() throws Exception {
        int fromPostcode = 50000;
        int toPostcode = 55000;

        List<Battery> batteries = new ArrayList<Battery>();

        Mockito.when(batteryService.getBatteriesInPostcodeRange(fromPostcode, toPostcode)).thenReturn(batteries);
        Mockito.when(batteryService.totalCapacityInWatt(batteries)).thenReturn(0.0);
        Mockito.when(batteryService.averageCapacityInWatt(batteries)).thenReturn(0.0);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get("/batteries")
                .param("fromPostcode", Long.toString(fromPostcode))
                .param("toPostcode", Long.toString(toPostcode))
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(jsonPath("$.batteries").isEmpty())
                .andExpect(jsonPath("$.totalCapacity").value(0.0))
                .andExpect(jsonPath("$.averageCapacity").value(0.0))
                .andReturn();
    }

    @Test
    void saveBatteries_success() throws Exception {
        List<Battery> batteries = getTestBatteries();
        List<Battery> batteriesWithNullIds = batteries.stream().map(battery -> Battery.builder()
                        .id(null)
                        .name(battery.getName())
                        .postcode(battery.getPostcode())
                        .capacityInWatt(battery.getCapacityInWatt())
                        .build())
                .collect(Collectors.toList());
        List<BatteryDto> batteriesDto = batteries.stream()
                .map(battery -> BatteryMapper.map(battery))
                .collect(Collectors.toList());

        Mockito.when(batteryService.save(batteriesWithNullIds)).thenReturn(batteries);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/batteries")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(batteriesDto));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andReturn();
    }

    private List<Battery> getTestBatteries(){
        Battery battery1=new Battery(1l,"Battery 1",302033,100.0);
        Battery battery2=new Battery(2l,"Battery 2",302040,200.0);
        Battery battery3=new Battery(3l,"Battery 3",402033,300.0);

        List<Battery> batteries= Arrays.asList(battery1,battery2,battery3);

        return batteries;
    }
}
