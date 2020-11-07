package me.kwiatkowsky.OxygenHub.Config;

import me.kwiatkowsky.OxygenHub.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.userDetailsService(customUserDetailsService)
                .authorizeRequests()
                .antMatchers("/index.html", "/index", "/", "/api/**",
                        "/webjars/jquery/3.3.1-1/jquery.min.js",
                        "/webjars/bootstrap/4.1.0/js/bootstrap.min.js",
                        "/webjars/bootstrap/4.1.0/css/bootstrap.min.css",
                        "/css/**", "/images/**", "/js/**", "favicon.ico",
                        "/fonts/**", "/img/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/app/login").permitAll()
                .successForwardUrl("/app/logging")
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();
    }
}
