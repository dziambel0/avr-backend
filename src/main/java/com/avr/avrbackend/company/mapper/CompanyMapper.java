package com.avr.avrbackend.company.mapper;

import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.domain.CompanyDto;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public static Company mapToCompany (CompanyDto companyDto){
        return Company.builder()
                .companyName(companyDto.getCompanyName())
                .contactInfo(companyDto.getContactInfo())
                .build();
    }

    private static CompanyDto mapToComapanyDto (Company company){
        return new CompanyDto(
                company.getCompanyId(),
                company.getCompanyName(),
                company.getContactInfo()
        );
    }
}
