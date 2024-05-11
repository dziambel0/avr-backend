package com.avr.avrbackend.order.controller;

import com.avr.avrbackend.order.domain.OrderDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final List<OrderDto> orders = new ArrayList<>();

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orders;
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable Long orderId) {
        for (OrderDto order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        throw new RuntimeException("Order not found for id: " + orderId);
    }

    @PostMapping
    public void addOrder(@RequestBody OrderDto orderDto) {
        orders.add(orderDto);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId().equals(orderId)) {
                orders.set(i, orderDto);
                return;
            }
        }
        throw new RuntimeException("Order not found for id: " + orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orders.removeIf(order -> order.getOrderId().equals(orderId));
    }

    @PostMapping("/calculate-price")
    public double calculatePrice(@RequestBody OrderDto orderDto) {
        // Logika obliczania ceny transakcji
        return 0.0; // Przykładowa cena
    }

    @PutMapping("/{orderId}/assign-car")
    public void assignCarToOrder(@PathVariable Long orderId, @RequestParam Long carId) {
        // Logika przypisywania samochodu do transakcji
    }

    @PutMapping("/{orderId}/assign-company")
    public void assignCompanyToOrder(@PathVariable Long orderId, @RequestParam Long companyId) {
        // Logika przypisywania firmy do transakcji
    }
}
