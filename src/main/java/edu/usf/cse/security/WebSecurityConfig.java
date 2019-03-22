package edu.usf.cse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final String secretToken;

    private final UserDetailsService userDetailsService;

    private final String[] permitAllPatterns = {
            "/js/**", "/css/**", "/html/**", "/images/**", "/favicon.ico", "/", "/index.html", "/user/current",
            "/user/login", "/create-cx", "/create", "/test"
    };

    @Autowired
    public WebSecurityConfig(@Value("${token.secretToken") String secretToken, UserDetailsService userDetailsService) {
        this.secretToken = secretToken;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Bean
    public TokenHandler tokenHandler() {
        return new TokenHandler(secretToken, userDetailsService);
    }

    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService(tokenHandler());
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenAuthenticationService());
    }

    @Bean
    public TokenAuthenticationProcessingFilter tokenAuthenticationProcessingFilter() throws Exception {
        TokenHandler tokenhandler = new TokenHandler(secretToken, userDetailsService);
        TokenAuthenticationService authenticationService = new TokenAuthenticationService(tokenhandler);
        return new TokenAuthenticationProcessingFilter("/user/login",
                tokenAuthenticationService(), authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().cacheControl().disable()
                .and()
                .authorizeRequests()

                .antMatchers("/js/**", "/css/**", "/html/**", "/images/**", "/favicon.ico", "/", "/create-cx").permitAll()

                .antMatchers(HttpMethod.GET, "/index.html", "/user/current").permitAll()

                .antMatchers(HttpMethod.POST, "/user/login", "/create").permitAll()

                //.antMatchers(HttpMethod.GET, "/create").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")

                .antMatchers(HttpMethod.POST, "/read").access("hasAnyRole('ROLE_ADMIN','ROLE_USER')")

                .antMatchers(HttpMethod.PATCH, "/update", "/restore").access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.DELETE, "/delete", "/clear").access("hasRole('ROLE_ADMIN')")

                .antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources",
                        "/configuration/security", "/swagger-ui.html", "/webjars/**",
                        "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security").permitAll()   // for testing

                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .requireCsrfProtectionMatcher(new NoAntPathRequestMatcher(permitAllPatterns))
                .and()
                .addFilterBefore(tokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(csrfFilter(permitAllPatterns), FilterSecurityInterceptor.class)
                .addFilterAfter(new CsrfHeaderFilter(), SessionManagementFilter.class);
    }

    private RequestMatcher csrfProtectionMatcher(String[] patterns) {
        return new NoAntPathRequestMatcher(patterns);
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    private CsrfFilter csrfFilter(String[] patterns) {
        CsrfFilter csrfFilter = new CsrfFilter(csrfTokenRepository());
        csrfFilter.setRequireCsrfProtectionMatcher(csrfProtectionMatcher(patterns));
        return csrfFilter;
    }

}
