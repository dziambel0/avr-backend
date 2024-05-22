package com.avr.avrbackend.services;

import com.avr.avrbackend.company.domain.Company;
import com.avr.avrbackend.company.domain.CompanyDto;
import com.avr.avrbackend.company.mapper.CompanyMapper;
import com.avr.avrbackend.company.repository.CompanyRepository;
import com.avr.avrbackend.company.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompanyServiceTestSuite {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompanies() {
        Company company = new Company();
        List<Company> companies = Arrays.asList(company);

        when(companyRepository.findAll()).thenReturn(companies);

        List<Company> result = companyService.getAllCompanies();

        assertEquals(companies, result);
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    void testGetCompanyById() {
        Long companyId = 1L;
        Company company = new Company();

        when(companyRepository.findById(companyId)).thenReturn(Optional.of(company));

        Optional<Company> result = companyService.getCompanyById(companyId);

        assertEquals(Optional.of(company), result);
        verify(companyRepository, times(1)).findById(companyId);
    }

    @Test
    void testGetCompanyByIdNotFound() {
        Long companyId = 1L;

        when(companyRepository.findById(companyId)).thenReturn(Optional.empty());

        Optional<Company> result = companyService.getCompanyById(companyId);

        assertEquals(Optional.empty(), result);
        verify(companyRepository, times(1)).findById(companyId);
    }

    @Test
    void testCreateCompany() {
        Company company = new Company();

        when(companyRepository.save(company)).thenReturn(company);

        Company result = companyService.createCompany(company);

        assertEquals(company, result);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testUpdateCompany() {
        Company company = new Company();

        when(companyRepository.save(company)).thenReturn(company);

        Company result = companyService.updateCompany(company);

        assertEquals(company, result);
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testDeleteCompany() {
        Long companyId = 1L;

        companyService.deleteCompany(companyId);

        verify(companyRepository, times(1)).deleteById(companyId);
    }

    private final CompanyMapper companyMapper = new CompanyMapper();

    @Test
    void testMapToCompany() {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setCompanyName("Example Company");
        companyDto.setContactInfo("123-456-789");

        Company company = companyMapper.mapToCompany(companyDto);

        assertEquals("Example Company", company.getCompanyName());
        assertEquals("123-456-789", company.getContactInfo());
    }

    @Test
    void testMapToCompanyDto() {
        Company company = Company.builder()
                .companyId(1L)
                .companyName("Example Company")
                .contactInfo("123-456-789")
                .build();

        CompanyDto companyDto = companyMapper.mapToComapanyDto(company);

        assertEquals(1L, companyDto.getCompanyId());
        assertEquals("Example Company", companyDto.getCompanyName());
        assertEquals("123-456-789", companyDto.getContactInfo());
    }
}
