package com.avr.avrbackend.order.domain;

import com.avr.avrbackend.cars.domain.Car;
import com.avr.avrbackend.company.domain.Company;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "AVR_ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID", unique = true)
    private Long orderId;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "PRICE")
    private double price;

    @OneToMany(mappedBy = "order")
    private List<Car> cars;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

}
