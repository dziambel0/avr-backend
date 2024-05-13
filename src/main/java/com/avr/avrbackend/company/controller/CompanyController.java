package com.avr.avrbackend.company.controller;

import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.domain.CompanyDto;
import com.avr.avrbackend.company.mapper.CompanyMapper;
import com.avr.avrbackend.company.service.CompanyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    private final CompanyMapper companyMapper;

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getListOfCompanies(){
        List<Company> companyList = companyService.getAllCompanies();
        List<CompanyDto> dtos = companyList.stream()
                .map(companyMapper::mapToComapanyDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(value = "{companyId}")
    public ResponseEntity<CompanyDto> getCompany(@PathVariable Long companyId){
        Company company = companyService.getCompanyById(companyId)
                .orElseThrow(()->new RuntimeException("Comapny not found by id:" + companyId));
        CompanyDto dto = companyMapper.mapToComapanyDto(company);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> createCompany(@RequestBody CompanyDto companyDto){
        Company company = companyMapper.mapToCompany(companyDto);
        companyService.createCompany(company);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{companyId}")
    public ResponseEntity<CompanyDto> updateCompany(@PathVariable Long companyId, @RequestBody CompanyDto companyDto){
        Company existingCompany = companyService.getCompanyById(companyId)
                .orElseThrow(()-> new RuntimeException("Company not foud by id: " + companyId));

        Company updatedCompany = companyMapper.mapToCompany(companyDto);
        updatedCompany.setCompanyId(existingCompany.getCompanyId());

        Company savedCompany = companyService.updateCompany(updatedCompany);
        CompanyDto savedDto = companyMapper.mapToComapanyDto(savedCompany);
        return ResponseEntity.ok(savedDto);
    }

    @DeleteMapping(value = "{companyId}")
    public ResponseEntity<Void> deleteCompany(@PathVariable Long companyId){
        companyService.deleteCompany(companyId);
        return ResponseEntity.noContent().build();
    }
}
