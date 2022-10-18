package com.choidh.securityinflearn.controller;

import com.choidh.securityinflearn.WithUser;
import com.choidh.securityinflearn.account.Account;
import com.choidh.securityinflearn.account.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    AccountService accountService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void index() throws Exception {
        mockMvc.perform(get("/").with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void index_user() throws Exception {
        mockMvc.perform(get("/").with(user("choidh").roles("USER")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void admin_user() throws Exception {
        mockMvc.perform(get("/admin").with(user("adminUser").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUser
    void admin_admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void login() throws Exception {
        Account account = accountService.createAccount(Account.builder()
                .username("choidh")
                .password("1234")
                .role("USER")
                .build());

        mockMvc.perform(formLogin().user(account.getUsername()).password("1234"))
                .andDo(print())
                .andExpect(authenticated());
    }

}