package com.example.AuthServer.config;
import com.example.AuthServer.security.JwtAuthenticationEntryPoint;
import com.example.AuthServer.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                                authorize
//                                .anyRequest().permitAll()
//                                        .requestMatchers(HttpMethod.POST,"/api/auth/roles").hasAuthority("admin")
                                        .requestMatchers(HttpMethod.PUT, "/api/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
                                        .requestMatchers(HttpMethod.GET,"/api/v1/employees/fetch-all").hasAuthority("admin")
                                        .requestMatchers(HttpMethod.POST,"/api/v1/salary/").hasAuthority("accountant")
                                        .requestMatchers(HttpMethod.PUT,"/api/v1/salary/").hasAuthority("accountant")
                                        .requestMatchers(HttpMethod.GET,"/api/v1/salary/getAll").hasAuthority("accountant")

                                        .anyRequest().authenticated()
                ).exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}

