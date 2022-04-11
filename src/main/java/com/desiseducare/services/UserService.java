package com.desiseducare.services;
import com.desiseducare.models.User;

import com.desiseducare.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * performs basic user functions
 */
@Service
@Slf4j
public class UserService
{
    @Autowired
    UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Function to add a user to the database
     * @param user object of type user to be added to the database
     */
    public String addUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "";
    }
    /**
     * Function that lists all users registered on the platform
     * @return returns a list of all users
     */
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
    /**
     * Function that returns a user given userID
     * @param id integer representing userID of the user
     * @return returns the user object
     */
    public User getUser(Integer id)
    {
        System.out.println(id);
        return userRepository.getById(id);
    }

    public User getUserByEmail(String email)
    {
        System.out.println("Fetch user with email: " + email);
        return  userRepository.getByEmail(email);

    }

    /**
     * Function that deletes the user given userID
     * @param id integer representing userID of the user
     */
    public String deleteUser(Integer id)
    {
        if(userRepository.existsById(id) == true)
        {
            userRepository.deleteById(id);
            return "deleted";
        }
        else
        {
            return "no such user exists";
        }
    }
}
