package com.desiseducare.config;


import com.desiseducare.models.Company;
import com.desiseducare.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Configuration
public class CompanyConfig
{

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner commandLineRunner2(CompanyRepository repository)
    {
        return args->
        {


            Company Oracle = new Company("Oracle");

            Oracle.setPassword(passwordEncoder.encode("oracle"));
            repository.saveAll(List.of(Oracle));
        };
    }
}