package com.nic.zuul.security;

import com.nic.zuul.security.filter.LoginFilter;
import com.nic.zuul.security.handler.CustomAuthenticationEntryPoint;
import com.nic.zuul.security.handler.FailureHandler;
import com.nic.zuul.security.handler.LogoutHandler;
import com.nic.zuul.security.handler.SuccessHandler;
import com.nic.zuul.security.service.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("cn")
//                .password("123").roles("admin");
//    }
    @Autowired
    CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    LogoutHandler logoutHandler;
    @Autowired
    SuccessHandler successHandler;
    @Autowired
    FailureHandler failureHandler;
    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/someOtherApi/**","/actuator/health");///actuator/health 为consul健康检查路径，未放行将导致服务注册失败
    }
    @Autowired
    DetailsService detailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService);//指定
    }
    @Bean//configuration类注解下 表示方法加入IOC 
    LoginFilter getLoginFilter() throws Exception {

        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(successHandler);
        loginFilter.setAuthenticationFailureHandler(failureHandler);
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");//顶掉之前的配置
        ConcurrentSessionControlAuthenticationStrategy sessionStrategy = new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategy);
        return loginFilter;
    }
    @Bean
    SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }
//    @Autowired
//    UserService userService;
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService()
//    {
//    	//security 初始化数据库sql varchar_ignorecase 更换成对应数据库类型
////     	create table users(username varchar_ignorecase(50) not null primary key,password varchar_ignorecase(500) not null,enabled boolean not null);
////    	create table authorities (username varchar_ignorecase(50) not null,authority varchar_ignorecase(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
////    	create unique index ix_auth_username on authorities (username,authority);
//    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//    	manager.createUser(User.withUsername("cn").password("123").roles("guest").build());
//        manager.createUser(User.withUsername("java").password("123").roles("admin").build());
//        manager.createUser(User.withUsername("boy").password("123").roles("user").build());
//        return manager;
//    }
    //admin 继承user权限
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/api2/admin/**").hasAnyRole("admin")
        .antMatchers("/api2/user/**").hasAnyRole("user")
         .antMatchers("/user/**").hasAnyRole("user")
         .antMatchers("/admin/**").hasAnyRole("admin")
        //**	匹配多层路径
        //*	匹配一层路径
        //?	匹配任意单个字符
                .anyRequest().authenticated()//antMatchers只能在anyRequest之前配置，之后无意义
                .and()
                .formLogin()
               // .successHandler(successHandler)
               // .failureHandler(failureHandler)
                .loginProcessingUrl("/login")//指定登陆接口
               // .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logOut")
                .logoutSuccessHandler(logoutHandler)
                .permitAll()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                ;
        http.addFilterAt(getLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}