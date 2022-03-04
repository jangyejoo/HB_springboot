package com.example.hb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.hb.filters.JwtRequestFilter;
import com.example.hb.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	 	@Autowired
	    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	    @Autowired
	    private MyUserDetailsService myUserDetailsService;

	    @Autowired
	    private JwtRequestFilter jwtRequestFilter;


	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
	    }
	    
	    @Override
	    public void configure(WebSecurity web) throws Exception {
	    	web.ignoring().antMatchers("/signup/**");
	    	web.ignoring().antMatchers("/idcheck/**");
	    	web.ignoring().antMatchers("/emailcheck/**");
	    	web.ignoring().antMatchers("/find_id/**");
	    	web.ignoring().antMatchers("/find_pwd/**");
	    	web.ignoring().antMatchers("/mail/**");
	    	web.ignoring().antMatchers("/issue/**");
	    }

	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Override
	    @Bean
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }


	    @Override
	    protected void configure(HttpSecurity httpSecurity) throws Exception {
	        httpSecurity.csrf().disable()
	                .authorizeRequests().antMatchers("/login").permitAll().
	                anyRequest().authenticated().and().
	                exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    }
}
