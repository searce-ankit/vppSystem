package com.example.vppsystem.repositories;

import com.example.vppsystem.model.Battery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatteryRepository  extends JpaRepository<Battery, Long> {

    /**
     *
     * Returns battery list sorted by battery name within postcodes.
     *
     * @param {long} Parameter for fromPostcode
     * @param {long} Parameter for toPostcode
     *
     */
    List<Battery> findByPostcodeBetweenOrderByNameAsc(long fromPostcode, long toPostcode);
}
