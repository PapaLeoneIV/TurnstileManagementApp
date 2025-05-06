package it.tdgc.turnstile.controller;

import it.tdgc.turnstile.dto.CompanyDTO;
import it.tdgc.turnstile.model.Company;
import it.tdgc.turnstile.service.CompanyService;
import it.tdgc.turnstile.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Transactional
@RequestMapping(path = "/company")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }




    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<CompanyDTO>>> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/search/id/{id}")
    public ResponseEntity<ApiResponse<CompanyDTO>> getCompanyById(@PathVariable("id") Integer id) {
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<ApiResponse<CompanyDTO>> deleteCompanyById(@PathVariable Integer id) {
        return companyService.deleteCompanyById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CompanyDTO>> updateCompany(@RequestBody Company company) {
        return companyService.updateCompany(company);
    }

    @PostMapping("/insert")
    public ResponseEntity<ApiResponse<CompanyDTO>> insertCompany(@RequestBody CompanyDTO company) {
        return companyService.insertCompany(company);
    }
}
