package com.example.hb.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.hb.dao.MemberDao;
import com.example.hb.models.TokenResponse;
import com.example.hb.util.JwtUtil;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
    private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MemberDao memberDao;
	
	 @Autowired
	    private MyUserDetailsService userDetailsService;
	
	public TokenResponse issueAccessToken(HttpServletRequest request){
        String accessToken = jwtTokenUtil.resolveAccessToken(request);
        String refreshToken = jwtTokenUtil.resolveRefreshToken(request);
        System.out.println("accessToken = " + accessToken);
        System.out.println("refreshToken = " + refreshToken);
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급(refreshToken의 내용을 통해서)
        if(!jwtTokenUtil.validateToken(accessToken)){  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요없다.
            System.out.println("Access 토큰 만료됨");
            if(jwtTokenUtil.validateRefreshToken(refreshToken)){     //들어온 Refresh 토큰이 유효한지
                System.out.println("Refresh 토큰은 유효함");
                Claims claimsToken = jwtTokenUtil.getClaimsFormToken(refreshToken);
                String userId = (String)claimsToken.get("username");
                String tokenFromDB = memberDao.getRefresh(userId);
                System.out.println("tokenFromDB = " + tokenFromDB);
                if(refreshToken.equals(tokenFromDB)) {   //DB의 refresh토큰과 지금들어온 토큰이 같은지 확인
                    System.out.println("Access 토큰 재발급 완료");
                    accessToken = jwtTokenUtil.generateToken(userId);
                    
                    TokenResponse response = new TokenResponse();
                    response.setJwt(accessToken);
                    response.setJwt_refresh(refreshToken);
                    response.setResult("new access token");
                    
                    return response;
                }
                else{
                    //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                    System.out.println("Refresh Token Tampered");
                    //예외발생
                }
            }
            else{
                //입력으로 들어온 Refresh 토큰이 유효하지 않음
            	System.out.println("refresh 토큰이 만료되었습니다.");
            	TokenResponse response = new TokenResponse();
                response.setResult("expired refresh token");
                
                return response;
            }
        }
        TokenResponse response = new TokenResponse();
        response.setJwt(accessToken);
        response.setJwt_refresh(refreshToken);
        response.setResult("good token");
        
        return response;
    }

}
