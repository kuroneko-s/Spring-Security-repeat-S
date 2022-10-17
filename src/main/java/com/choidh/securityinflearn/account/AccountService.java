package com.choidh.securityinflearn.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username + "에 해당하는 유저를 찾을 수 없습니다.");
        }

        return User.builder()
                .username(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole())
                .build();
    }

    @Autowired
    private PasswordEncoder encodePassword;

    public Account createAccount(Account account) {
        account.encodePassword(encodePassword);

        return repository.save(account);
    }
}
