package com.avr.avrbackend.company.controller;

import com.avr.avrbackend.company.domain.CompanyDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final List<CompanyDto> companies  = new ArrayList<>();

    @GetMapping
    public List<CompanyDto> getAllCompanies() {
        return companies;
    }

    @GetMapping("/{companyId}")
    public CompanyDto getCompanyById(@PathVariable Long companyId) {
        for (CompanyDto company : companies) {
            if (company.getCompanyId().equals(companyId)) {
                return company;
            }
        }
        throw new RuntimeException("Company not found for id: " + companyId);
    }

    @PostMapping
    public void addCompany(@RequestBody CompanyDto companyDto) {
        companies.add(companyDto);
    }

    @PutMapping
            ("/{companyId}")
    public void updateCompany(@PathVariable Long companyId, @RequestBody CompanyDto companyDto) {
        for (int i = 0; i < companies.size(); i++) {
            if (companies.get(i).getCompanyId().equals(companyId)) {
                companies.set(i, companyDto);
                return;
            }
        }
        throw new RuntimeException("Company not found for id: " + companyId);
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Long companyId) {
        companies.removeIf(company -> company.getCompanyId().equals(companyId));
    }

}
