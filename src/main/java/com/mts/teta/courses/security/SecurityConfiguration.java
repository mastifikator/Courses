package com.mts.teta.courses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailService;

    @Autowired
    public SecurityConfiguration(UserAuthService userAuthService) {
        this.userDetailService = userAuthService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/registration").permitAll()
                .antMatchers("/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies()
                .and()
                .sessionManagement()
                .maximumSessions(1);
    }

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService);
//                .and()
//                .inMemoryAuthentication()
//                .withUser("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN");
    }
}
