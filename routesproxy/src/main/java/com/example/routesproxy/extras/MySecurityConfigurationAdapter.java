package com.example.routesproxy.extras;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
public class MySecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

    // https://www.thomasvitale.com/https-spring-boot-ssl-certificate/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().anyRequest().permitAll();
        http.addFilterBefore(new ExceptionHandlerFilter(), LogoutFilter.class);
    }

}