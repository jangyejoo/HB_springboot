// jwt를 생성하고 검증하는 역할 수행
package com.example.hb.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("jwt");
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        return request.getHeader("jwt_refresh");
    }
    
    /*
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    */
    
    public boolean isTokenExpired(String token) {
        System.out.println("isValidToken is : " +token);
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("username"));
            return false;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get("username"));
            return true;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }

    /*
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 테스트 용 1분 토큰
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails.getUsername());
    }
    
    public String createRefreshToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //사용할 암호화 알고리즘
                .compact();
    }
    
    /*
    public Boolean validateToken(String token, UserDetails userDetails) {
    	/*
    	final String mId = extractUsername(token);
        return (mId.equals(userDetails.getUsername()) && !isTokenExpired(token));  
       
    	try {
    		final String mId = extractUsername(token);
    		if(mId.equals(userDetails.getUsername())) return true;
    	} catch (ExpiredJwtException e) {
    		System.out.println("만료된 jwt 토큰입니다.");
    	} catch (IllegalArgumentException e) {
    		System.out.println("jwt 토큰이 잘못되었습니다.");
    	}
    	return false;
    }
    */
    
    public String generateToken(String userId) {
        Claims claims = Jwts.claims();//.setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("username", userId);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + 1000 * 60 * 1)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 사용할 암호화 알고리즘과
                .compact();
    }

    public String generateRefreshToken(String userId) {
        Claims claims = Jwts.claims();
        claims.put("username", userId); //
        Date now = new Date();
        Date expiration = new Date(now.getTime() + 1000 * 60 * 5);

        return Jwts.builder()
        		.setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
    
    public boolean validateToken(String token) {
        System.out.println("isValidToken is : " +token);
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("username"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get("username"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
    
    public boolean validateRefreshToken(String token) {
        try {
            Claims accessClaims = getClaimsFormToken(token);
            System.out.println("Access expireTime: " + accessClaims.getExpiration());
            System.out.println("Access userId: " + accessClaims.get("username"));
            return true;
        } catch (ExpiredJwtException exception) {
            System.out.println("Token Expired UserID : " + exception.getClaims().get("username"));
            return false;
        } catch (JwtException exception) {
            System.out.println("Token Tampered");
            return false;
        } catch (NullPointerException exception) {
            System.out.println("Token is null");
            return false;
        }
    }
    
    //refresh token 정보 얻어내기
    public Claims getClaimsFromJwtToken(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
    }
    
    public Claims getClaimsFormToken(String token) throws JwtException {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

}