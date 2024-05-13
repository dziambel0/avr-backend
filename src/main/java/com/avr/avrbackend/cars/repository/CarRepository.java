package com.avr.avrbackend.cars.repository;

import com.avr.avrbackend.cars.domain.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Long> {

    @Override
    List<Car> findAll();

    @Override
    Optional<Car> findById(Long id);

    @Override
    Car save(Car car);

}
