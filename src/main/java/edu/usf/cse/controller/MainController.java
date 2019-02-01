package edu.usf.cse.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class MainController {

    @RequestMapping(value = "/")
    public String index() {
        return "This is the first page";
    }

}