package com.avr.avrbackend.company.repository;

import com.avr.avrbackend.company.domain.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    @Override
    List<Company> findAll();

    @Override
    Optional<Company> findById(Long id);

    @Override
    Company save(Company company);

}
