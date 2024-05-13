package com.avr.avrbackend.order.repository;

import com.avr.avrbackend.order.domain.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Override
    List<Order> findAll();

}
