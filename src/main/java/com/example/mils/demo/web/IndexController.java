package com.example.mils.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping // ("/hello")
    // @ResponseBody // この下の文をそのまま返す
    public String index() {
        return "index"; // viewportであるindex.htmlを検索して返す
        // return "<h1>Hello World!</h1>";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/logout")
    public String showLogoutForm() {
        return "logout";
    }
}
