package com.avr.avrbackend.order.service;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.cars.service.CarService;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import com.avr.avrbackend.order.mapper.OrderMapper;
import com.avr.avrbackend.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final CarService carService;

    private final OrderMapper orderMapper;

    private final CarMapper carMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id){
        return orderRepository.findById(id);
    }

    public Order updateOrder(Order order){
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id){
        orderRepository.deleteById(id);
    }

    public ResponseEntity<OrderDto> addCarToOrder(Long orderId, Long carId){
        logger.info("Adding car {} to order {}", carId, orderId);

        Car car = carService.getCarById(carId)
                .orElseThrow(()-> new RuntimeException("Car not found by id: " + carId));
        logger.info("Retrieved car: {}", car);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found by id: " + orderId));
        logger.info("Retrieved order: {}", order);

        order.getCars().add(car);
        orderRepository.save(order);
        logger.info("Car added to order: {}", order);

        OrderDto orderDto = orderMapper.mapToOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

    public Order deleteCarFromOrder(Long orderId, Long carId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found id: " + orderId));

        Car car = carService.getCarById(carId)
                .orElseThrow(()-> new RuntimeException("Car not found by id: " + carId));

        order.getCars().remove(car);
        return orderRepository.save(order);
    }

    public Car getCarFromOrder(Long orderId, Long carId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found by id: " + orderId));

        return order.getCars().stream()
                .filter(car -> car.getCarId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car not found by id: " + carId));
    }

    public List<CarDto> getCarDtosFromOrder(Long orderId) {
        List<Car> carList = orderRepository.findById(orderId)
                .map(Order::getCars)
                .orElseThrow(() -> new RuntimeException("Cars not found in Order id: " + orderId));
        return carList.stream()
                .map(carMapper::mapToCarDto)
                .collect(Collectors.toList());
    }
}
