package com.avr.avrbackend.controllers;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarDto;
import com.avr.avrbackend.cars.mapper.CarMapper;
import com.avr.avrbackend.order.controller.OrderController;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import com.avr.avrbackend.order.mapper.OrderMapper;
import com.avr.avrbackend.order.service.CurrencyService;
import com.avr.avrbackend.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class OrderControllerTestSuite {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private CarMapper carMapper;

    @Mock
    private CurrencyService currencyService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfOrders() {
        Order order = new Order();
        OrderDto orderDto = new OrderDto();
        List<Order> orders = Arrays.asList(order);
        List<OrderDto> orderDtos = Arrays.asList(orderDto);

        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderMapper.mapToOrderDto(order)).thenReturn(orderDto);

        ResponseEntity<List<OrderDto>> response = orderController.getListOfOrders();

        assertEquals(ResponseEntity.ok(orderDtos), response);
        verify(orderService, times(1)).getAllOrders();
        verify(orderMapper, times(1)).mapToOrderDto(order);
    }

    @Test
    void testGetOrder() {
        Long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.mapToOrderDto(order)).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.getOrder(orderId);

        assertEquals(ResponseEntity.ok(orderDto), response);
        verify(orderService, times(1)).getOrderById(orderId);
        verify(orderMapper, times(1)).mapToOrderDto(order);
    }

    @Test
    void testCreateOrder() {
        OrderDto orderDto = new OrderDto();
        Order order = new Order();

        when(orderMapper.mapToOrder(orderDto)).thenReturn(order);

        ResponseEntity<Void> response = orderController.createOrder(orderDto);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(orderMapper, times(1)).mapToOrder(orderDto);
        verify(orderService, times(1)).createOrder(order);
    }

    @Test
    void testUpdateOrder() {
        Long orderId = 1L;
        OrderDto orderDto = new OrderDto();
        Order existingOrder = new Order();
        Order updatedOrder = new Order();
        Order savedOrder = new Order();
        OrderDto savedDto = new OrderDto();

        when(orderService.getOrderById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderMapper.mapToOrder(orderDto)).thenReturn(updatedOrder);
        when(orderService.updateOrder(updatedOrder)).thenReturn(savedOrder);
        when(orderMapper.mapToOrderDto(savedOrder)).thenReturn(savedDto);

        ResponseEntity<OrderDto> response = orderController.updateOrder(orderId, orderDto);

        assertEquals(ResponseEntity.ok(savedDto), response);
        verify(orderService, times(1)).getOrderById(orderId);
        verify(orderMapper, times(1)).mapToOrder(orderDto);
        verify(orderService, times(1)).updateOrder(updatedOrder);
        verify(orderMapper, times(1)).mapToOrderDto(savedOrder);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        ResponseEntity<Void> response = orderController.deleteOrder(orderId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(orderService, times(1)).deleteOrder(orderId);
    }

    @Test
    void testAddCarToOrder() {
        Long orderId = 1L;
        Long carId = 1L;
        OrderDto orderDto = new OrderDto();

        when(orderService.addCarToOrder(orderId, carId)).thenReturn(ResponseEntity.ok(orderDto));

        ResponseEntity<OrderDto> response = orderController.addCarToOrder(orderId, carId);

        assertEquals(ResponseEntity.ok(orderDto), response);
        verify(orderService, times(1)).addCarToOrder(orderId, carId);
    }

    @Test
    void testDeleteCarFromOrder() {
        Long orderId = 1L;
        Long carId = 1L;
        Car car = new Car();
        Order order = new Order();
        OrderDto orderDto = new OrderDto();

        when(orderService.getCarFromOrder(orderId, carId)).thenReturn(car);
        when(orderService.deleteCarFromOrder(orderId, carId)).thenReturn(order);
        when(orderMapper.mapToOrderDto(order)).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.deleteCarFromOrder(orderId, carId);

        assertEquals(ResponseEntity.ok(orderDto), response);
        verify(orderService, times(1)).getCarFromOrder(orderId, carId);
        verify(orderService, times(1)).deleteCarFromOrder(orderId, carId);
        verify(orderMapper, times(1)).mapToOrderDto(order);
    }

    @Test
    void testGetCarsFromOrder() {
        Long orderId = 1L;
        CarDto carDto = new CarDto();
        List<CarDto> carDtos = Arrays.asList(carDto);

        when(orderService.getCarDtosFromOrder(orderId)).thenReturn(carDtos);

        ResponseEntity<List<CarDto>> response = orderController.getCarsFromOrder(orderId);

        assertEquals(ResponseEntity.ok(carDtos), response);
        verify(orderService, times(1)).getCarDtosFromOrder(orderId);
    }

    @Test
    void testGetCarFromOrder() {
        Long orderId = 1L;
        Long carId = 1L;
        Car car = new Car();
        CarDto carDto = new CarDto();

        when(orderService.getCarFromOrder(orderId, carId)).thenReturn(car);
        when(carMapper.mapToCarDto(car)).thenReturn(carDto);

        ResponseEntity<CarDto> response = orderController.getCarFromOrder(orderId, carId);

        assertEquals(ResponseEntity.ok(carDto), response);
        verify(orderService, times(1)).getCarFromOrder(orderId, carId);
        verify(carMapper, times(1)).mapToCarDto(car);
    }

    @Test
    void testAssignCompanyToOrder() {
        Long orderId = 1L;
        Long companyId = 1L;
        Order order = new Order();

        when(orderService.assignComapnyToOrder(orderId, companyId)).thenReturn(order);

        Order response = orderController.assignCompanyToOrder(orderId, companyId);

        assertEquals(order, response);
        verify(orderService, times(1)).assignComapnyToOrder(orderId, companyId);
    }

    @Test
    void testCalculateOrderPrice() {
        Long orderId = 1L;

        orderController.calculateOrderPrice(orderId);

        verify(orderService, times(1)).calculateOrderPrice(orderId);
    }
}
