package com.zk.configuration.security;

import com.zk.common.password.SM4PasswordEncoder;
import com.zk.configuration.auth.token_granter.MobileAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private MobileAuthenticationProvider mobileAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.formLogin().permitAll()
//                //禁用匿名用户
//                .and().anonymous().disable()
//                /*.loginProcessingUrl("/oauth/token")*/
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable()
//                // “session”管理的设置
//                .sessionManagement()
//                // 不让“jsessionid”出现在URL中，等同于XML配置里的：disable-url-rewriting="true"
////                .enableSessionUrlRewriting(true)
//                // 基于token，所以不需要session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().permitAll()
                .loginProcessingUrl("/oauth/token")
                .and()
                .anonymous().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, // 允许对于网站静态资源的无授权访问
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js",
//                        "/swagger-resources/**"
//                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                // “session”管理的设置
                .sessionManagement()
                // 不让“jsessionid”出现在URL中，等同于XML配置里的：disable-url-rewriting="true"
//                .enableSessionUrlRewriting(true)
                // 基于token，所以不需要session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        // 增加自定义的 AuthenticationProvider
        auth.authenticationProvider(mobileAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SM4PasswordEncoder();
    }

    /**
     * 跨域
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
    }

}