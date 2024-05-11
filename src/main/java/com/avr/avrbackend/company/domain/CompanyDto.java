package com.avr.avrbackend.company.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CompanyDto {

    private Long companyId;

    private String companyName;

    private String contactInfo;

}
