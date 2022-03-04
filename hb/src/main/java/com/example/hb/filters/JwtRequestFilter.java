package com.example.hb.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.hb.service.MyUserDetailsService;
import com.example.hb.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{
	 	@Autowired
	    private MyUserDetailsService userDetailsService;

	 	@Autowired
	    private JwtUtil jwtUtil;
	    
		@Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {
	        final String authorizationHeader = request.getHeader("Authorization");
	        HttpServletRequest httpRequest = (HttpServletRequest)request;
	        
	        String mId= null;
	        String jwt = null;
	        
	        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
		            jwt = authorizationHeader.substring(7);
		            mId = jwtUtil.extractUsername(jwt);
	        }
	        if(mId != null && SecurityContextHolder.getContext().getAuthentication() == null){
	        	UserDetails userDetails = this.userDetailsService.loadUserByUsername(mId);
	        	try {
		            if(jwtUtil.validateToken(jwt)){
		                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
		                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		            }
	        	} catch(ExpiredJwtException e) {
	        		System.out.println("만료된 토큰입니다.");
	        	}
	        	
	        }
	        chain.doFilter(request,response);
        	
        }
	   
	    /*
	    @Override
	    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    	Collection<String> excludeUrlPatterns = new LinkedHashSet<>();
	        excludeUrlPatterns.add("/signup/**");

	        return excludeUrlPatterns.stream()
	                                 .anyMatch(pattern -> new AntPathMatcher().match(pattern, request.getServletPath()));
	    }
	    */
}
