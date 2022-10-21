package com.choidh.securityinflearn.configuration;


import com.choidh.securityinflearn.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                        .mvcMatchers("/", "/info").permitAll()
                        .mvcMatchers("/admin").hasRole("ADMIN")
                        .mvcMatchers("/account/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin()
                .defaultSuccessUrl("/")
                .and()
                .httpBasic();

        return http.build();
    }

    // @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsService() {
        UserDetails user1 = User.withUsername("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .build();

        UserDetails user2 = User.withUsername("user")
                .password("{noop}!@#")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
