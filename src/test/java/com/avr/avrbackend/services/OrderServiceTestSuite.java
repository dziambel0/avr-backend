package com.avr.avrbackend.services;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.cars.domain.CarStatus;
import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.repository.CompanyRepository;
import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import com.avr.avrbackend.order.mapper.OrderMapper;
import com.avr.avrbackend.order.repository.OrderRepository;
import com.avr.avrbackend.order.service.CurrencyService;
import com.avr.avrbackend.order.service.OrderService;
import com.avr.avrbackend.order.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTestSuite {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateOrder(){
        Order order = new Order();

        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);

        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetAllOrders() {
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        assertEquals(Collections.singletonList(order), orderService.getAllOrders());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(orderId);

        assertTrue(result.isPresent());
        assertEquals(order, result.get());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testGetOrderByIdNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        Optional<Order> result = orderService.getOrderById(orderId);

        assertFalse(result.isPresent());
        verify(orderRepository, times(1)).findById(orderId);
    }

    @Test
    void testUpdateOrder() {
        Order order = new Order();

        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateOrder(order);

        assertEquals(order, result);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;

        orderService.deleteOrder(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testAssignComapnyToOrder() {
        Long orderId = 1L;
        Long companyId = 1L;
        Order order = new Order();
        Company company = new Company();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.assignComapnyToOrder(orderId, companyId);

        assertEquals(order, result);
        assertEquals(company, order.getCompany());
        verify(orderRepository, times(1)).findById(orderId);
        verify(companyRepository, times(1)).findById(companyId);
        verify(orderRepository, times(1)).save(order);
    }

    private final OrderMapper orderMapper = new OrderMapper();

    @Test
    void testMapToOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setStartDate(LocalDate.of(2024, 5, 1));
        orderDto.setEndDate(LocalDate.of(2024, 5, 10));
        orderDto.setPrice(1000.0);

        Order order = orderMapper.mapToOrder(orderDto);

        assertEquals(LocalDate.of(2024, 5, 1), order.getStartDate());
        assertEquals(LocalDate.of(2024, 5, 10), order.getEndDate());
        assertEquals(1000.0, order.getPrice());
    }

    @Test
    void testMapToOrderDto() {
        List<Car> cars = new ArrayList<>();
        cars.add(Car.builder()
                .carId(1L)
                .brand("Toyota")
                .model("Camry")
                .carYear(2020)
                .registrationNumber("ABC123")
                .carStatus(CarStatus.ACTIVE)
                .build());

        Order order = Order.builder()
                .orderId(1L)
                .startDate(LocalDate.of(2024, 5, 1))
                .endDate(LocalDate.of(2024, 5, 10))
                .price(1000.0)
                .cars(cars)
                .build();

        OrderDto orderDto = orderMapper.mapToOrderDto(order);

        assertEquals(1L, orderDto.getOrderId());
        assertEquals(LocalDate.of(2024, 5, 1), orderDto.getStartDate());
        assertEquals(LocalDate.of(2024, 5, 10), orderDto.getEndDate());
        assertEquals(1000.0, orderDto.getPrice());
        assertEquals(1, orderDto.getCars().size());
        assertEquals(1L, orderDto.getCars().get(0).getCarId());
        assertEquals("Toyota", orderDto.getCars().get(0).getBrand());
        assertEquals("Camry", orderDto.getCars().get(0).getModel());
        assertEquals(2020, orderDto.getCars().get(0).getCarYear());
        assertEquals("ABC123", orderDto.getCars().get(0).getRegistrationNumber());
        assertEquals(CarStatus.ACTIVE, orderDto.getCars().get(0).getCarStatus());
    }

}
