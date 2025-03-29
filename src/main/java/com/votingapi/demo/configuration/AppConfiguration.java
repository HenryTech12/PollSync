package com.votingapi.demo.configuration;

import com.votingapi.demo.service.MyUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfiguration {

    private final String[] publicUrls = {
       "/user/add", "/user/**", "/css/**", "/js/**", "login.html", "signup.html", "reset.html","/h2-console/**"
    };

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        System.out.println("security configuration initialized");
        security.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request ->
                    request.requestMatchers(publicUrls).permitAll()
                            .anyRequest().authenticated()
                ).headers(headers -> headers.frameOptions(frame -> frame.disable()));

        security.formLogin(login -> login.loginPage("/user/signin")
                .loginProcessingUrl("/user/login")
                .failureUrl("/user/signin?error=true")
                .defaultSuccessUrl("/home", true)
                .permitAll());

        security.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/user/home").invalidateHttpSession(true).
                deleteCookies("JSESSIONID"));

        return security.build();
    }

}
