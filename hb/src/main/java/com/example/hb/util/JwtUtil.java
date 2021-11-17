// jwt를 생성하고 검증하는 역할 수행
package com.example.hb.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    public String createRefreshToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims) //정보 저장
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())) //토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + 336000 * 60 * 60 * 10)) //토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //사용할 암호화 알고리즘
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String mId = extractUsername(token);
        return (mId.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    
    //refresh token 정보 얻어내기
    public Claims getClaimsFromJwtToken(String jwtToken) throws JwtException {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
    }
    
}