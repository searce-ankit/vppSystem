package com.example.vppsystem.services;

import com.example.vppsystem.model.Battery;

import java.util.List;

public interface BatteryService {

    /**
     *
     * Saves battery list provided and returns created batteries.
     *
     * @param {List<Battery>} Parameter for battery List.
     *
     */
    List<Battery> save(List<Battery> batteries);

    /**
     *
     * Returns battery list sorted by battery name within range of postcodes.
     *
     * @param {long} Parameter for fromPostcode
     * @param {long} Parameter for toPostcode
     *
     */
    List<Battery> getBatteriesInPostcodeRange(long fromPostcode, long toPostcode);

    /**
     *
     * Returns total watt capacity for provided batteries.
     *
     * @param {List<Battery>} Parameter for battery list
     *
     */
    double totalCapacityInWatt(List<Battery> batteries);

    /**
     *
     * Returns average watt capacity for provided batteries.
     *
     * @param {List<Battery>} Parameter for battery list
     *
     */
    double averageCapacityInWatt(List<Battery> batteries);
}
