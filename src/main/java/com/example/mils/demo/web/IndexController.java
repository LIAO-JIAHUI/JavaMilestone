package com.example.mils.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.mils.demo.domain.milestone.MilestoneEntity;
import com.example.mils.demo.domain.milestone.MilestoneService;
import com.example.mils.demo.web.user.SignupForm;
import com.example.mils.demo.web.user.UserGlobalEntity;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class IndexController {
    private final MilestoneService milestoneService;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("index");
        modelAndView.addObject("SignupForm", new SignupForm());

        return modelAndView;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String showLogoutForm() {
        return "logout";
    }

    @GetMapping("/debug")
    public String showDebug() {
        return "debug";
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false) String order) {

        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("status", status);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("order", order);
        UserGlobalEntity user = (UserGlobalEntity) model.getAttribute("userHash");
        List<MilestoneEntity> milestoneList = milestoneService.search(title, user.getUsername(), status, orderBy,
                order);
        model.addAttribute("milestoneList", milestoneList);
        return "dashboard";
    }
}
