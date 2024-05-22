package com.avr.avrbackend.controllers;

import com.avr.avrbackend.company.controller.CompanyController;
import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.domain.CompanyDto;
import com.avr.avrbackend.company.mapper.CompanyMapper;
import com.avr.avrbackend.company.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompanyControllerTestSuite {

    @Mock
    private CompanyService companyService;

    @Mock
    private CompanyMapper companyMapper;

    @InjectMocks
    private CompanyController companyController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetListOfCompanies() {
        Company company = new Company();
        CompanyDto companyDto = new CompanyDto();
        List<Company> companies = Arrays.asList(company);
        List<CompanyDto> companyDtos = Arrays.asList(companyDto);

        when(companyService.getAllCompanies()).thenReturn(companies);
        when(companyMapper.mapToComapanyDto(company)).thenReturn(companyDto);

        ResponseEntity<List<CompanyDto>> response = companyController.getListOfCompanies();

        assertEquals(ResponseEntity.ok(companyDtos), response);
        verify(companyService, times(1)).getAllCompanies();
        verify(companyMapper, times(1)).mapToComapanyDto(company);
    }

    @Test
    void testGetCompany() {
        Long companyId = 1L;
        Company company = new Company();
        CompanyDto companyDto = new CompanyDto();

        when(companyService.getCompanyById(companyId)).thenReturn(Optional.of(company));
        when(companyMapper.mapToComapanyDto(company)).thenReturn(companyDto);

        ResponseEntity<CompanyDto> response = companyController.getCompany(companyId);

        assertEquals(ResponseEntity.ok(companyDto), response);
        verify(companyService, times(1)).getCompanyById(companyId);
        verify(companyMapper, times(1)).mapToComapanyDto(company);
    }

    @Test
    void testCreateCompany() {
        CompanyDto companyDto = new CompanyDto();
        Company company = new Company();

        when(companyMapper.mapToCompany(companyDto)).thenReturn(company);

        ResponseEntity<Void> response = companyController.createCompany(companyDto);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(companyMapper, times(1)).mapToCompany(companyDto);
        verify(companyService, times(1)).createCompany(company);
    }

    @Test
    void testUpdateCompany() {
        Long companyId = 1L;
        CompanyDto companyDto = new CompanyDto();
        Company existingCompany = new Company();
        Company updatedCompany = new Company();
        Company savedCompany = new Company();
        CompanyDto savedDto = new CompanyDto();

        when(companyService.getCompanyById(companyId)).thenReturn(Optional.of(existingCompany));
        when(companyMapper.mapToCompany(companyDto)).thenReturn(updatedCompany);
        when(companyService.updateCompany(updatedCompany)).thenReturn(savedCompany);
        when(companyMapper.mapToComapanyDto(savedCompany)).thenReturn(savedDto);

        ResponseEntity<CompanyDto> response = companyController.updateCompany(companyId, companyDto);

        assertEquals(ResponseEntity.ok(savedDto), response);
        verify(companyService, times(1)).getCompanyById(companyId);
        verify(companyMapper, times(1)).mapToCompany(companyDto);
        verify(companyService, times(1)).updateCompany(updatedCompany);
        verify(companyMapper, times(1)).mapToComapanyDto(savedCompany);
    }

    @Test
    void testDeleteCompany() {
        Long companyId = 1L;

        ResponseEntity<Void> response = companyController.deleteCompany(companyId);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(companyService, times(1)).deleteCompany(companyId);
    }

}
