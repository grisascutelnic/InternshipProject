package com.example.project.config;

//import com.application.art.security.CustomUserDetailsService;
//import com.application.art.service.UserService;
//import com.application.art.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/css/**", "/js/**", "/images/**", "/plugins/**").permitAll()
                                .requestMatchers("/index", "/error").permitAll()
                                .requestMatchers("/register/**").not().authenticated()
                                .requestMatchers("profile/{id}/events/**").authenticated()
                                .requestMatchers("/login/**", "/announcements/**", "/profile/**", "/viewAnnouncement/**",
                                        "/feedbacks/**", "/allAnnouncements/**", "/loginSuccess/**", "/change-password/**",
                                        "/delete-account/**").permitAll()
//                                .requestMatchers("/users").hasRole("ADMIN")
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/loginSuccess", true)
                                .loginProcessingUrl("/login")
//                                .successHandler(customAuthenticationSuccessHandler)
                                .permitAll()
                ).logout(
                        logout -> logout
                                .logoutUrl("/logout")
                                .invalidateHttpSession(true)  // Închide sesiunea curentă
                                .deleteCookies("JSESSIONID")  // Șterge cookie-ul sesiunii
                                .logoutSuccessUrl("/login?logout=true")
                                .permitAll()
                );
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}