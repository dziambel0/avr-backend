package com.avr.avrbackend.cars.domain;

import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "AVR_CARS")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAR_ID", unique = true)
    private Long carId;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "YEAR")
    private int year;

    @Column(name = "EWGISTRATION_NUMBER")
    private String registrationNumber;

    @Column(name ="STATUS")
    private Enum<CarStatus> statusEnum;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;
}
