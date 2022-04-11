package com.desiseducare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlerController {

    @GetMapping("/403")
    public String accessDenied()
    {
        return "403";
    }
}
