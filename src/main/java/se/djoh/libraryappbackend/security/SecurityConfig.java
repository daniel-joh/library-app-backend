package se.djoh.libraryappbackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.djoh.libraryappbackend.service.impl.JpaUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JpaUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().disable();
        http
                .authorizeRequests(authorize -> {
                    authorize
                            //.antMatchers("/h2-console/**").permitAll()          //to enable h2-console
                            .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                            .antMatchers("/api/login").permitAll()
                            .antMatchers(HttpMethod.POST, "/api/users").permitAll()              //anyone should be allowed to create a user
                            .antMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("ADMIN", "USER")

                            .antMatchers(HttpMethod.GET, "/api/books**").hasAnyRole("ADMIN", "USER")

                            .antMatchers(HttpMethod.GET, "/api/genres").hasAnyRole("ADMIN", "USER")

                            .antMatchers(HttpMethod.POST, "/api/loans").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.PATCH, "/api/loans/**").hasAnyRole("ADMIN", "USER")
                            .antMatchers(HttpMethod.GET, "/api/loans**").hasAnyRole("ADMIN", "USER");
                })
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //for h2 console
        //http.headers().frameOptions().sameOrigin();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}