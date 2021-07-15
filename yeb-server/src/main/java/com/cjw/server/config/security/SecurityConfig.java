package com.cjw.server.config.security;
import com.cjw.server.config.security.component.*;
import com.cjw.server.pojo.Admin;
import com.cjw.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private CustomFilter customFilter;
    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用jwt不需要csrf 关闭
      http.csrf()
              .disable()
              //基于token 不需要session
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeRequests()
              //所有请求都要认证
              .anyRequest()
              .authenticated()
              //动态权限配置
              .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                  @Override
                  public <O extends FilterSecurityInterceptor> O postProcess(O obj) {
                      obj.setAccessDecisionManager(customUrlDecisionManager);
                      obj.setSecurityMetadataSource(customFilter);
                      return obj;
                  }
              })
              .and()
              //禁用缓存
              .headers()
              .cacheControl();
        //添加jwt登录授权拦截器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权喝未登录结果返回
        http.exceptionHandling()
                //当访问接口没有权限时，自定义返回结果
                .accessDeniedHandler(restfulAccessDeniedHandler)
                //当没有登录或token访问接口，自定义返回结果
                .authenticationEntryPoint(restAuthorizationEntryPoint);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
            return username ->{
              Admin admin= adminService.getAdminByUserName(username);
                    if (null!=admin){
                        admin.setRoles(adminService.getRoles(admin.getId()));
                        return admin;
                    }
                   throw new UsernameNotFoundException("用户名或密码不正确!");
            };
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthencationTokenFilter();
    }
}
