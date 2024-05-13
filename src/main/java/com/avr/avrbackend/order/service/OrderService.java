package com.avr.avrbackend.order.service;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

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

    public Order addCarToOrder(Long orderId, Car car){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found id: " + orderId));
        order.getCars().add(car);
        return orderRepository.save(order);
    }

    public Order deleteCarFromOrder(Long orderId, Car car){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not found id: " + orderId));
        order.getCars().remove(car);
        return orderRepository.save(order);
    }

    public List<Car>getCarsFromOrder(Long orderId){
        List<Car> carList = orderRepository.findById(orderId)
                .map(Order::getCars).orElseThrow(()->new RuntimeException("Cars not found in Order id :" + orderId));
        return carList;
    }

    public Car getCarFromOrder(Long orderId, Long carId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("Order not foud by id; " + orderId));
        for (Car car: order.getCars()){
            if (car.getCarId().equals(carId)){
                return car;
            }
        }
        throw new RuntimeException("Car not fout by id:" + carId);
    }

}
