package com.ihordev.web;

import com.ihordev.core.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/")
    public String main(Model model, HttpServletRequest request) {
        return (RequestUtils.isAjaxRequest(request)) ? "greetings :: #main__content" : "greetings";
    }

}
