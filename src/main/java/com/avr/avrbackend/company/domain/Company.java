package com.avr.avrbackend.company.domain;

import com.avr.avrbackend.order.domain.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity(name = "AVR_COMPANIES")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID", unique = true)
    private Long companyId;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "CONTACT_INFO")
    private String contactInfo;

    @OneToMany(mappedBy = "company")
    private List<Order> orders;

}
