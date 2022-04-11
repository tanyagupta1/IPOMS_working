package com.desiseducare.config;

import com.desiseducare.models.User;
import com.desiseducare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.List;

@Configuration
public class UserConfig {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository)
    {
        return args->{
//            User tanya = new User(1,"1234","abc@gmail.com","Angel", 0.0f);
//            User tana = new User(2,"134","abfgc@gmail.com","RII", 0.0f);
//            User arch = new User(3,"3333","ab12@gmail.com","NII", 0.0f);
//            tanya.setPassword(passwordEncoder.encode(tanya.getPassword()));
//            tana.setPassword(passwordEncoder.encode(tana.getPassword()));
//            arch.setPassword(passwordEncoder.encode(arch.getPassword()));
//            repository.saveAll(List.of(tanya,tana,arch));
        };
    }
}
