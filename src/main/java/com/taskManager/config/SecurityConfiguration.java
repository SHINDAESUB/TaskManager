package com.taskManager.config;
import com.taskManager.web.api.authenticate.AuthenticationFilter;
import com.taskManager.web.api.authenticate.UserAuthenticationFailureHandler;
import com.taskManager.web.api.authenticate.UserAuthenticationSuccessHandler;
import com.taskManager.web.api.authenticate.UserLogoutSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/* 스프링 시큐리티 설정 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    /* 모두에게 허용된 경로*/
    private static final String[] PUBLIC = new String[]{ "/error" ,"/login", "/logout", "/join" , "/api/join" };
    
    /* HTTP 요청에 기반하여 접근*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .authorizeRequests()
          .antMatchers(PUBLIC).permitAll() // PUBLIC 에 설정한 HTTP 요청은 누구나 허용
          .anyRequest().authenticated()    // PUBLIC 을 제외한 경로는 인증 필요 설정
        .and()
          .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
          .formLogin()
          .loginPage("/login")  //로그인 URL 경로 걸정
        .and()
          .logout()
          .logoutUrl("/logout") //로그아웃 URL 경로 걸정
          .logoutSuccessHandler(logoutSuccessHandler()) //로그아웃 이후 리다이렉트 경로 설정 
        .and()
          .csrf().disable();
    }
  
    @Override
    public void configure(WebSecurity web) {
      web.ignoring().antMatchers("/static/**", "/js/**", "/css/**", "/images/**", "/favicon.ico");
    }

    /* password 암호화 등록 : password 생성시 BCrypt 해싱 함수를 활용한다. */
    @Bean
    public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
    }
    /* 인증 필터 */
    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
      AuthenticationFilter authenticationFilter = new AuthenticationFilter();
      authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
      authenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
      authenticationFilter.setAuthenticationManager(authenticationManagerBean());
      return authenticationFilter;
    }
  
    /*인증 성공 핸들러 */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
      return new UserAuthenticationSuccessHandler(); 
    }

    /*인증 실패 핸들러 */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
      return new UserAuthenticationFailureHandler();
    }
  
    /*로그아웃 핸들러 */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
      return new UserLogoutSuccessHandler();
    }
}