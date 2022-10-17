package com.choidh.securityinflearn.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Slf4j
@Controller
public class SampleController {

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        if ( principal == null ) {
            model.addAttribute("message", "choidh hello");
        } else {
            model.addAttribute("message", principal.getName() + " hello");
            log.info(principal.toString());
        }


        return "index";
    }

    @GetMapping("/info")
    public String info(Model model) {
        model.addAttribute("message", "info hello");

        return "info";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("message", principal.getName() + " dashboard hello");

        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        model.addAttribute("message", principal.getName() + " admin hello");

        return "admin";
    }
}
