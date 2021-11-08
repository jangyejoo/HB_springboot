// db에서 userDetail을 얻어와 authenticationManager에게 제공하는 역할
package com.example.hb.service;

import java.util.ArrayList;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.hb.dao.MemberDao;
import com.example.hb.dto.LoginDto;
import com.example.hb.models.AuthenticationRequest;

@Service
@MapperScan(basePackages="com.example.hb.dao")
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
    @Override
    public UserDetails loadUserByUsername(String mId) throws UsernameNotFoundException {
    	LoginDto user = memberDao.authenticate(mId);
    	if(user==null) {
    		throw new UsernameNotFoundException("User not found with username: " + mId);
    	} else {
    		return new User(user.getmId(),  passwordEncoder.encode(user.getmPwd()) ,new ArrayList<>()); 
    	}
    }
}