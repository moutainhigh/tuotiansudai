package com.tuotiansudai.console.activity.config;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.tuotiansudai.spring.MyAccessDeniedHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/**").permitAll();

        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler());
    }

    private MyAccessDeniedHandler myAccessDeniedHandler() {
        MyAccessDeniedHandler accessDeniedHandler = new MyAccessDeniedHandler();
        accessDeniedHandler.setErrorPageMapping(Maps.newHashMap(new ImmutableMap.Builder<String, String>()
                .put("/", "/login")
                .build()));
        accessDeniedHandler.setErrorPage("/403");
        return accessDeniedHandler;
    }
}
