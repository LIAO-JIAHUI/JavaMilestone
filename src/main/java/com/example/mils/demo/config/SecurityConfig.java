package com.example.mils.demo.config;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
// extends WebSecurityConfigurerAdapter
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)
                        throws Exception {
                http.formLogin(login -> login
                                .loginProcessingUrl("/login")
                                .loginPage("/login")
                                // .successForwardUrl("/milestones")
                                .defaultSuccessUrl("/milestones")// , true
                                .failureUrl("/login?error")
                                .permitAll()).logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login"))
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                                                .permitAll()
                                                .requestMatchers("/ws/**").permitAll()
                                                .requestMatchers("/js/**").permitAll()// jsに直接アクセスできないようにする
                                                // .requestMatchers("/api/**").permitAll() // api アクセス
                                                .requestMatchers("/index").permitAll()
                                                .requestMatchers("/login").permitAll()
                                                .requestMatchers("/user/**").permitAll()
                                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                                                .hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .headers(headers -> headers.frameOptions().disable())
                                .csrf(csrf -> csrf
                                                .ignoringRequestMatchers(
                                                                AntPathRequestMatcher.antMatcher("/h2-console/**")));
                return http.build();
        }

        // @Bean
        // RememberMeServices rememberMeServices(UserDetailsService userDetailsService)
        // {
        // RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        // TokenBasedRememberMeServices rememberMe = new
        // TokenBasedRememberMeServices(myKey, userDetailsService,
        // encodingAlgorithm);
        // rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
        // return rememberMe;
        // }

        // @Override
        // protected void configure(HttpSecurity http)throws Exception {
        // http
        // .authorizeRequests()
        // .mvcMachers("/login**").permitAll()
        // .anyRequest().auutheticated()
        // .and()
        // .formLogin()
        // .loginPage("/login");
        // }
}