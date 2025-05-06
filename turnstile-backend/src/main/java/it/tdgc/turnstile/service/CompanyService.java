package it.tdgc.turnstile.service;

import it.tdgc.turnstile.dto.CompanyDTO;
import it.tdgc.turnstile.model.Company;
import it.tdgc.turnstile.repository.CompanyRepository;
import it.tdgc.turnstile.util.ApiResponse;
import it.tdgc.turnstile.util.MapperInterface;
import it.tdgc.turnstile.util.responseBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    final private CompanyRepository companyRepository;
    private final MapperInterface mapperInterface;


    @Autowired
    public CompanyService(CompanyRepository companyRepository, MapperInterface mapperInterface) {
        this.companyRepository = companyRepository;
        this.mapperInterface = mapperInterface;
    }

    @Transactional
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAllCompanies() {
        List<Company> c = companyRepository.findAll();
        if(c.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.OK, "No companies found", null);
        }
        List<CompanyDTO> cDTOS = c.stream().map(mapperInterface::toCompanyDTO).toList();
        return responseBuilder.buildResponse(HttpStatus.OK, "OK", cDTOS);
    }

    @Transactional
    public ResponseEntity<ApiResponse<CompanyDTO>> getCompanyById(Integer id) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Company ID not found", null, new Date(), null));
        }

        CompanyDTO companyDTO = mapperInterface.toCompanyDTO(company.get());

        return ResponseEntity.ok(new ApiResponse<>("200", "Company found", companyDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<CompanyDTO>> deleteCompanyById(Integer id) {
        if(id == null || id < 0){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", null);
        }
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NOT_FOUND, "Company ID not found", null);
        }
        companyRepository.deleteById(id);
        CompanyDTO companyDTO = mapperInterface.toCompanyDTO(company.get());

        return responseBuilder.buildResponse(HttpStatus.OK, "OK", companyDTO);
    }

    @Transactional
    public ResponseEntity<ApiResponse<CompanyDTO>> updateCompany(Company company) {
        Optional<Company> companyToUpdate = companyRepository.findById(company.getId());
        if (companyToUpdate.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("404", "Company ID not found", null, new Date(), null));
        }
        Company updatedCompany = companyToUpdate.get();
        updatedCompany.setName(company.getName());
        updatedCompany.setAddress(company.getAddress());
        companyRepository.save(updatedCompany);
        CompanyDTO companyDTO = mapperInterface.toCompanyDTO(companyToUpdate.get());

        return ResponseEntity.ok(new ApiResponse<>("204", "Company updated successfully", companyDTO, new Date(), null));
    }

    @Transactional
    public ResponseEntity<ApiResponse<CompanyDTO>> insertCompany(CompanyDTO company) {
        if(company.getAddress().isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Company address is empty", null);
        }
        if(company.getName().isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Company name is empty", null);
        }
        if(companyRepository.findByName(company.getName()).isPresent()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Company name already exists", null);
        }
        if(companyRepository.findByAddress(company.getAddress()).isPresent()){
            return  responseBuilder.buildResponse(HttpStatus.BAD_REQUEST, "Company address already exists", null);
        }

        Company newCompany = new Company();
        newCompany.setName(company.getName());
        newCompany.setAddress(company.getAddress());

        Company savedCompany = companyRepository.save(newCompany);
        CompanyDTO companyDTO = mapperInterface.toCompanyDTO(savedCompany);

        return responseBuilder.buildResponse(HttpStatus.OK, "Company created successfully", companyDTO);
    }
}

