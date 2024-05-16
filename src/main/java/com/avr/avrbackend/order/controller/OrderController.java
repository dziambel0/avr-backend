package com.avr.avrbackend.order.controller;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import com.avr.avrbackend.order.mapper.OrderMapper;
import com.avr.avrbackend.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    private final CarMapper carMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @GetMapping
    public ResponseEntity<List<OrderDto>> getListOfOrders(){
        List<Order> orderList = orderService.getAllOrders();
        List<OrderDto> dtos = orderList.stream()
                .map(orderMapper::mapToOrderDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId){
        Order order = orderService.getOrderById(orderId)
                .orElseThrow(()->new RuntimeException("Order not foud by id: " + orderId));
        OrderDto dto = orderMapper.mapToOrderDto(order);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderDto orderDto){
        Order order = orderMapper.mapToOrder(orderDto);
        orderService.createOrder(order);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto){
        Order existingOrder = orderService.getOrderById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found by id: " + orderId));

        Order updateeOrder = orderMapper.mapToOrder(orderDto);
        updateeOrder.setOrderId(existingOrder.getOrderId());

        Order savedOders = orderService.updateOrder(updateeOrder);
        OrderDto savedDto = orderMapper.mapToOrderDto(savedOders);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping(value = "{orderId}")
    public ResponseEntity<Void> deleteOrder (@PathVariable Long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/add/{orderId}/{carId}")
    public ResponseEntity<OrderDto> addCarToOrder(@PathVariable Long orderId, @PathVariable Long carId){
        logger.info("Adding car {} to order {}", carId, orderId);
        return orderService.addCarToOrder(orderId, carId);
    }

    @PutMapping(value = "/delete/{orderId}/{carId}")
    public ResponseEntity<OrderDto> deleteCarFromOrder(@PathVariable Long orderId, @PathVariable Long carId){
        Car car = orderService.getCarFromOrder(orderId, carId);
        Order order = orderService.deleteCarFromOrder(orderId, carId);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @GetMapping(value = "{orderId}/cars")
    public ResponseEntity<List<CarDto>> getCarsFromOrder(@PathVariable Long orderId){
        List<CarDto> carDtos = orderService.getCarDtosFromOrder(orderId);
        return ResponseEntity.ok(carDtos);
    }

    @GetMapping(value = "{orderId}/cars/{carId}")
    public ResponseEntity<CarDto> getCarFromOrder(@PathVariable Long orderId, @PathVariable Long carId){
        Car car = orderService.getCarFromOrder(orderId, carId);
        CarDto dto = carMapper.mapToCarDto(car);
        return ResponseEntity.ok(dto);
    }

    @PutMapping(value = "/{orderId}/assignCompany/{companyId}")
    public Order assignCompanyToOrder(@PathVariable Long orderId, @PathVariable Long companyId){
        return orderService.assignComapnyToOrder(orderId, companyId);
    }
}
