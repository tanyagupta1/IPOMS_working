package com.desiseducare.config;


import com.desiseducare.services.UserDetailsServiceImpl;
import com.desiseducare.services.CompanyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true)
/**
 * Security configuration using Spring security for Users(Bidders) and Companies
 */
public class SecurityConfiguration{

    /** defining and registering password encoder (using Bcrypt strong hashing)
    * BcryptEncoder can be replaced by NoOpEncoder(no encoding) and Standard Encoder(SHA-256)
    **/
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Configuration
    @Order(2)
    public static class UserWebSecurityConfiguration extends  WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsServiceImpl userDetailsService;


        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }



        /**
         * User configuration
         * URLs starting with /user/ can only be access by users with ROLE_USER.
         * login page - /user/login/
         * logout page - /user/logout
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.authorizeRequests().antMatchers("/user/**").hasAuthority("USER").and().exceptionHandling().accessDeniedPage("/403");
            http
                    .antMatcher("/user/**")
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .permitAll()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/user/welcome")
                    .and()
                    .logout()
                    .logoutUrl("/user/logout")
                    .logoutSuccessUrl("/")
                    .permitAll();
        }

    }



    @Configuration
    @Order(1)
    public class CompanyWebSecurityConfiguration extends WebSecurityConfigurerAdapter{

        @Autowired
        private CompanyDetailsService companyDetailsService;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(companyDetailsService).passwordEncoder(passwordEncoder);
        }
            @Bean
            public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
         }

        /**
         * Company configuration
         * URLs starting with /company/ can only be access by users with ROLE_COMPANY.
         * login page - /company/login/
         * logout page - /company/logout
         */
        @Override
        protected void configure(final HttpSecurity http) throws Exception {

            http.csrf().disable();


            http.authorizeRequests().antMatchers("/company/**").hasAuthority("COMPANY").and().exceptionHandling().accessDeniedPage("/403");
            http
                    .antMatcher("/company/**")
                    .authorizeRequests().anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/company/login")
                    .permitAll()
                    .usernameParameter("name")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/company/welcome")
                    .and()
                    .logout()
                    .logoutUrl("/company/logout")
                    .logoutSuccessUrl("/")
                    .permitAll();

        }




    }


}