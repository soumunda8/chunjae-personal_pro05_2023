package com.chunjae.project05.configuration;

import com.chunjae.project05.biz.UserService;
import com.chunjae.project05.domain.UserFailLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    // 인증 처리와 암호화
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return authenticationProvider;
    }

    // 인증 관리자
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider(userService));
    }

    @Bean
    public UserFailLogin loginFailHandler(){
        return new UserFailLogin();
    }

    //접근 보안 설정 관리자
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/**","/user/login.do","/user/registration.do").permitAll()
                .mvcMatchers("/css/**","/js/**","/images/**","/fonts/**","/clEditor/**","/upload/**").permitAll()
                .antMatchers("/admin/home.do").hasAuthority("ADMIN") // ADMIN 권한의 유저만 /home 에 접근가능
                .anyRequest().authenticated()
                //.and().csrf().disable().cors().disable()
                .and().formLogin()
                .loginPage("/user/login.do")
                .failureHandler(loginFailHandler())
                //.failureUrl("/user/login.do?error=true")
                .defaultSuccessUrl("/")
                .usernameParameter("loginId")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout.do"))
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/access-denied");
    }

}