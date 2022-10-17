package com.choidh.securityinflearn.controller;

import com.choidh.securityinflearn.account.Account;
import com.choidh.securityinflearn.account.AccountRepository;
import com.choidh.securityinflearn.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/account/{username}/{password}/{role}")
    public Account createAccount(@ModelAttribute Account account) {
        return service.createAccount(account);
    }
}
