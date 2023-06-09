package com.pinellia.config;

import com.pinellia.common.security.JwtAuthenticationEntryPoint;
import com.pinellia.common.security.PasswordEncoder;
import com.pinellia.common.security.handler.JWTLogoutSuccessHandler;
import com.pinellia.common.security.handler.JWTAccessDeniedHandler;
import com.pinellia.common.security.handler.LoginFailureHandler;
import com.pinellia.common.security.handler.LoginSuccessHandler;
import com.pinellia.filter.JwtAuthenticationFilter;
import com.pinellia.filter.JwtOncePerRequestFilter;
import com.pinellia.filter.JwtLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //启动方法级别的权限认证
public class SecurityConfig {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private JWTLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtOncePerRequestFilter jwtOncePerRequestFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new JwtAuthenticationFilter(authenticationManager);
    }

    //密码明文加密配置
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder();
    }

    //白名单
    private static final String[] URL_WHITELIST = {
            "/login",
            "/logout",
            "/static/**",
            "/avatar/*",
            "/files/**"
    };

    /**
     * 拦截规则配置
     **/
    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                //跨域配置
                .cors()
                .and()
                //关闭csrf
                .csrf()
                .disable()

                // 登录配置
                .formLogin().permitAll()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                //登出
                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)

                // 禁用session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //无状态

                // 配置拦截规则
                .and()
                .authorizeRequests()
                .antMatchers("/static/**", "/avatar/*", "/files/**", "/logout").authenticated()
                .antMatchers(URL_WHITELIST).permitAll()   //白名单放行
                .anyRequest().authenticated()

                // 异常处理器
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)     //匿名访问，未登录处理
                .accessDeniedHandler(jwtAccessDeniedHandler)        //登陆后访问，无权限处理

                // 配置自定义的过滤器
                .and()
                .addFilter(jwtAuthenticationFilter(authenticationManager))
                .addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(UserAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }

    private JwtLoginFilter UserAuthenticationFilterBean() throws Exception {
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter();
        jwtLoginFilter.setAuthenticationManager(authenticationManager);
        jwtLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        jwtLoginFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return jwtLoginFilter;
    }
}