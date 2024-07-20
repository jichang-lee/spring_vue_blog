package com.chang.log.config;

import com.chang.log.domain.User;
import com.chang.log.exception.AlreadyExistsEmail;
import com.chang.log.exception.UserNotFound;
import com.chang.log.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers("/favicon.ico")
                .requestMatchers("/error");
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http.csrf(csrf -> csrf.disable())

               .authorizeHttpRequests()
                   .requestMatchers("/**").permitAll()
                   .anyRequest().authenticated()
               .and()
               .formLogin()
                   .loginPage("/auth/login")
                   .loginProcessingUrl("/auth/login")
                   .defaultSuccessUrl("/")
                   .usernameParameter("email")
                   .passwordParameter("password")
               .and()
               .rememberMe(rm -> rm.rememberMeParameter("remember")
                       .alwaysRemember(false)
                       .tokenValiditySeconds(2592000)
               )
               .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


//    @Bean
//    public UserDetailsService userDetailsService(UserRepository userRepository){
//        return username -> {
//           User user = userRepository.findByEmail(username)
//                    .orElseThrow(UserNotFound::new);
//           return new UserPrincipal(user);
//        };
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder (){
//        return new SCryptPasswordEncoder(
//                16,
//                8,
//                1,
//                32,
//                64
//        );
        return new BCryptPasswordEncoder();
    }
}
