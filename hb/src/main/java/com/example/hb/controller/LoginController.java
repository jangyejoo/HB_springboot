package com.example.hb.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.example.hb.dao.MemberDao;
import com.example.hb.dto.LoginDto;
import com.example.hb.dto.MemberDto;
import com.example.hb.models.AuthenticationRequest;
import com.example.hb.models.AuthenticationResponse;
import com.example.hb.service.MyUserDetailsService;
import com.example.hb.util.JwtUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
@CrossOrigin
public class LoginController {
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;
    
    @PostMapping("/login")
    @ResponseBody
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );

        } catch (BadCredentialsException e) {
            //throw new Exception("Incorrect username or password", e);
        	LoginDto user = memberDao.authenticate(authenticationRequest.getUsername());
        	AuthenticationResponse response = new AuthenticationResponse();
        	if(user==null) {
        		response.setResult(300);
        		response.setToken("notoken");
        		return response;
        	} 
        	response.setResult(400);
    		response.setToken("notoken");
            return response;
        	
        }
        AuthenticationResponse response = new AuthenticationResponse();
        
        final UserDetails userDetails = userDetailsService
       			.loadUserByUsername(authenticationRequest.getUsername());
       	final String jwt = jwtTokenUtil.generateToken(userDetails);
        System.out.println(userDetails);
       
       	response.setResult(200);
        response.setToken(jwt);
        return response;
        
    }
    
    @RequestMapping(value="/find_id", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public String findid(@RequestParam("mEmail") String mEmail) throws Exception{
		String result;
		result=memberDao.findId(mEmail);
		result = result.replaceAll("(?<=.{3})." , "*");
		return result;
	}
    
    @RequestMapping(value="/find_pwd", method=RequestMethod.POST, produces="application/json; charset=UTF-8")
	@ResponseBody
	public RedirectView findpwd(@RequestParam("mId")String mId,@RequestParam("mEmail") String mEmail) throws Exception{
		String result;
		result=memberDao.findPwd(mId,mEmail);
		return new RedirectView("/mail/send?email="+mEmail+"&&pwd="+result);
    }
   
}
