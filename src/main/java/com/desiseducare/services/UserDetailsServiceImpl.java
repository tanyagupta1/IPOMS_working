package com.desiseducare.services;

import com.desiseducare.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * Loads company UserDetails based on email of the user(bidder)
     * UserDetails include email,password and list of Granted Authorities for the user
     * @param email
     * @return UserDetails Object for the user
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user= userService.getUserByEmail(email);
        if(user==null)
        {

            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        String role="USER";

            String[] roles = role.split(" ");
            for (int i = 0; i < roles.length; i++) {
                GrantedAuthority authority = new SimpleGrantedAuthority(roles[i]);
                grantList.add(authority);
            }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),grantList
        );
    }
}
