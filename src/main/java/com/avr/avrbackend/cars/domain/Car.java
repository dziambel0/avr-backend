package com.avr.avrbackend.cars.domain;

import com.avr.avrbackend.order.domain.Order;
import com.avr.avrbackend.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "CAR_YEAR")
    private int carYear;

    @Column(name = "REGISTRATION_NUMBER")
    private String registrationNumber;

    @Column(name ="STATUS")
    private CarStatus carStatus;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToMany(mappedBy = "cars")
    private List<Order> orders;
}
