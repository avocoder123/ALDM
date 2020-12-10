package com.scheduleapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestBody String info){
        String[] infotokens = info.split("&");
        String user = infotokens[0].substring(5);
        String pass = infotokens[1].substring(5); //validation not implemented
        if(user.equals("admin")){
            return "redirect:/admin";
        }
        if(user.equals("00000000")) {
            return "redirect:/classDisplay";
        }
        return "index";
    }
}
