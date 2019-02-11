package com.gps.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {
	
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");  // TODO: lock down before deploying
        config.addAllowedHeader("*");
        config.addExposedHeader(HttpHeaders.AUTHORIZATION);
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public JwtSignInFilter signInFilter() throws Exception {
        return new JwtSignInFilter(
            new AntPathRequestMatcher("/sign-in"),
            authenticationManager()
        );
    }

    @Bean
    public JWTAuthenticationFilter authFilter() {
        return new JWTAuthenticationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/ws/*").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                signInFilter(),
                UsernamePasswordAuthenticationFilter.class
            )
            .addFilterBefore(
                authFilter(),
                UsernamePasswordAuthenticationFilter.class
            );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	/*
	@Bean
	JWTAuthenticationFilter corsFilter() {
		JWTAuthenticationFilter filter = new JWTAuthenticationFilter();
        return filter;
    }


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		//.authorizeRequests().antMatchers("/api/salvar-usuario", "/api/login").permitAll()
		httpSecurity.csrf().disable().authorizeRequests()
		//.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
		.antMatchers("/api/salvar-usuario").permitAll()
		.antMatchers(HttpMethod.OPTIONS, "/api/login").permitAll()
		.antMatchers(HttpMethod.POST,"/api/login").permitAll()
		//.anyRequest().authenticated()
		.and()

		// filtra requisições de login
		.addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()),
				UsernamePasswordAuthenticationFilter.class)

		// filtra outras requisições para verificar a presença do JWT no header
		.addFilterBefore(new JWTAuthenticationFilter(),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// cria uma conta default
		auth.inMemoryAuthentication()
		.withUser("admin")
		.password("password")
		.

		roles("ADMIN");
	}*/
}