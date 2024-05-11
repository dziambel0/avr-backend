package com.avr.avrbackend.order.mapper;

import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.order.domain.OrderDto;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public static Order mapToOrder (OrderDto orderDto){
        return Order.builder()
                .startDate(orderDto.getStartDate())
                .endDate(orderDto.getEndDate())
                .price(orderDto.getPrice())
                .build();
    }

    public static OrderDto mapToOrderDto (Order order){
        return new OrderDto(
                order.getOrderId(),
                order.getStartDate(),
                order.getEndDate(),
                order.getPrice()
        );
    }
}
