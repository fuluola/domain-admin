package com.fuluola.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello world!
 *
 */
@Controller
public class App 
{

    @RequestMapping("/")
    String home() {
        return "redirect:/home/domainResult";
    }
}
