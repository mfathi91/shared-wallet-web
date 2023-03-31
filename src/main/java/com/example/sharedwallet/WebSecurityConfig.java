package com.example.sharedwallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private AppConfig appConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .anyRequest().authenticated()
                )
                .formLogin()
                .and()
                .addFilterBefore(new DefaultLoginPageGeneratingFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout(LogoutConfigurer::permitAll);

        http.csrf().disable();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        final List<UserDetails> userDetailsList = new ArrayList<>();
        for (final var user : appConfig.users()) {
            userDetailsList.add(User.builder()
                    .username(user.username())
                    .password(String.format("{noop}%s", user.password()))
                    .roles("USER")
                    .build());
        }
        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
