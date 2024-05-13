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

    @PutMapping
    public ResponseEntity<Void> createOrder(@PathVariable OrderDto orderDto){
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

    @PutMapping(value = "/add/{orderId}")
    public ResponseEntity<OrderDto> addCarToOrder(@PathVariable Long orderId, @RequestBody CarDto carDto){
        Car car = carMapper.mapToCar(carDto);
        Order order = orderService.addCarToOrder(orderId, car);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

    @PutMapping(value = "/delete/{orderId}/{carId}")
    public ResponseEntity<OrderDto> deleteCarFromOrder(@PathVariable Long orderId, @PathVariable Long carId){
        Car car = orderService.getCarFromOrder(orderId, carId);
        Order order = orderService.deleteCarFromOrder(orderId,car);
        return ResponseEntity.ok(orderMapper.mapToOrderDto(order));
    }

//    @GetMapping(value = "{orderId}")
//    public ResponseEntity<List<CarDto>> getCarsFromOrder(@PathVariable Long orderId){
//        List<Car> carList = orderService.getCarsFromOrder(orderId);
//        List<CarDto> dtos = carList.stream()
//                .map(carMapper::mapToCarDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(dtos);
//    }
}
