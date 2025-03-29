package com.votingapi.demo.controller;

import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.dto.UserProfile;
import com.votingapi.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest servletRequest) {
        userService.generateApplicationUrl(servletRequest);
        return "index";
    }

    @GetMapping("/topage")
    public String page(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "redirect:/user/logout?q=ureg";
    }

    @RequestMapping("/uregister")
    public String shouldRegister() {
        return "shouldRegister";
    }
    @PostMapping("/about")
    public String aboutWebsite(Model model) {

        return "about";
    }
    @PostMapping("/profile")
    public String getProfile(Principal principal, Model model) {

        UserDTO userDTO = new UserDTO(); userDTO.setUsername(principal.getName());
        UserProfile userProfile = userService.getProfile(userService.getUserDataByUsername(userDTO));
        model.addAttribute("userProfile", Objects.requireNonNull(userProfile));
        return "settings";
    }
}
