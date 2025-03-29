package com.votingapi.demo.controller;

import com.votingapi.demo.dto.UserDTO;
import com.votingapi.demo.dto.UserProfile;
import com.votingapi.demo.service.UserService;
import jakarta.validation.Valid;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute UserDTO userDTO) {
        System.out.println(userDTO.getUsername());
        model.addAttribute("userDTO", userDTO);
        return "login";
    }

    @GetMapping("/signin")
    public String showLoginPage(Model model, boolean error) {
        model.addAttribute("userDTO", new UserDTO());
        model.addAttribute("check", error);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(String q, Model model) {
        System.out.println("q: "+q);
            if (q.equals("ureg")) {
                model.addAttribute("userDTO", new UserDTO());
                return "redirect:/user/register";
            } else {
                model.addAttribute("userDTO", new UserDTO());
                return "redirect:/user/login";
            }
    }

    @PostMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "signup";
    }

    @GetMapping("/reset")
    public String reset(Model model) {
        model.addAttribute("userProfile", new UserProfile());
        return "reset";
    }

    @PostMapping("/reset/password")
    public String resetPwd(Model model, @ModelAttribute UserProfile userProfile) {
        boolean check = userService.updatePassword(userProfile);
        if(check)
            model.addAttribute("resetResponse", "Password Changed Successfully");
        else
            model.addAttribute("resetResponse", "Invalid Inputs");

        model.addAttribute("userProfile", userProfile);
        return "reset";
    }

    @PostMapping("/changePassword")
    public String changePwd(@ModelAttribute UserProfile userProfile, Model model, Principal principal) {
        boolean changed = userService.updatePassword(userProfile);
        if(changed)
            model.addAttribute("passwordResponse", "Password changed successfully");
        else
            model.addAttribute("passwordResponse", "Invalid input");

        UserDTO userDTO = new UserDTO(); userDTO.setUsername(principal.getName());
        model.addAttribute("userProfile", userService.getProfile(userService.getUserDataByUsername(userDTO)));

        return "settings";
    }


    @PostMapping("/add")
    public String addUser(Model model, @ModelAttribute @Valid UserDTO userDTO) {
        if(!Objects.isNull(userDTO)) {
            userService.createUser(userDTO);

        }
        model.addAttribute("userDTO", userDTO);
        return "login";
    }


    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }
}
