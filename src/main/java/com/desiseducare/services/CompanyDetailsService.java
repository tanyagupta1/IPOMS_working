package com.desiseducare.services;

import com.desiseducare.models.Company;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CompanyDetailsService implements UserDetailsService {

    @Autowired
    private CompanyService companyService;

    /**
     * Loads company UserDetails based on name of company
     * UserDetails include name,password and list of Granted Authorities for the company
     * @param companyName
     * @return UserDetails Object for the company
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String companyName) throws UsernameNotFoundException {


        Company company=companyService.getCompanyByName(companyName);
        if(company==null)
        {
           log.error("Company not found : "+companyName);
            throw new UsernameNotFoundException("Company not found");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        String role="COMPANY";

            String[] roles = role.split(" ");
            for (int i = 0; i < roles.length; i++) {

                GrantedAuthority authority = new SimpleGrantedAuthority(roles[i]);
                grantList.add(authority);
            }


        return new org.springframework.security.core.userdetails.User(
                company.getName(),company.getPassword(),grantList
        );


    }
}
