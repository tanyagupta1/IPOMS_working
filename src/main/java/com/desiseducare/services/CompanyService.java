package com.desiseducare.services;
import com.desiseducare.models.Company;
import com.desiseducare.repository.CompanyRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
/**
 * performs CRUD operations of the company in db
 */
public class CompanyService
{
    @Autowired
    CompanyRepository companyRepository;

    /**
     * Function to add company to the database
     * @param company object of type company to be added to the database
     */
    public String addCompany(Company company)
   {
        List<Company> existingCompany = companyRepository.findByNameIs(company.getName());
        if(!existingCompany.isEmpty()) return "company already exists";
        companyRepository.save(company);
        return "";
    }

    /**
     * Function that lists all companies registered on the platform
     * @return returns a list of all companies
     */
    public List<Company> getAllCompanies()
    {
        return companyRepository.findAll();
    }

    /**
     * Function that returns a company given companyID
     * @param id integer representing companyID of the company
     * @return returns the company object
     */

    public Company getCompany(Integer id)
    {
        return companyRepository.getById(id);
    }



    public Company getCompanyByName(String name)
    {
        List<Company> companies=companyRepository.findByNameIs(name);
        if(companies.isEmpty())return null;
        else return companies.get(0);
    }
    /**
     * Function that deletes the company given companyID
     * @param id integer representing companyID of the company
     */
    public void deleteCompany(Integer id)
    {
        if(companyRepository.existsById(id))
        {
            companyRepository.deleteById(id);
        }
    }
}
