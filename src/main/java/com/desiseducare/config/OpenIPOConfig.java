package com.desiseducare.config;

import com.desiseducare.models.OpenIPO;
import com.desiseducare.repository.OpenIpoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

@Configuration
public class OpenIPOConfig
{
    @Bean
    CommandLineRunner commandLineRunner3(OpenIpoRepository repository)
    {
        return args->
        {
//            Date startDate = new Date(122,03,01);
//            Date endDate = new Date(122,03,30);
//            OpenIPO Oracle = new OpenIPO(1,startDate,endDate,
//                    50,100f,120f,1000);
//            repository.saveAll(List.of(Oracle));
        };
    }
}