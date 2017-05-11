package com.ihordev.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MessageSource messageSource;

    // GetMapping ???
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main(Model model) {

        return "main";
    }

}