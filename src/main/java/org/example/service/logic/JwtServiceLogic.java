package org.example.service.logic;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.example.service.JwtService;
import org.example.util.exception.UnauthorizedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtServiceLogic implements JwtService {
    private String secretKey = "asfsadasdfasdfasdfasdfasdfasdfasfdasdf";
    private long exp = 1000L * 60 * 60;

    public String createToken(Integer id) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("userToken")
                .setExpiration(new Date(System.currentTimeMillis() + exp))
                .claim("id", id)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public Integer getUserId(String token) throws Exception {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new UnauthorizedException("유효하지 않은 인증토큰입니다.");
        }
        return Integer.valueOf(claims.get("id").toString());
    }

    public void checkValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
        } catch (Exception e) {
            throw new UnauthorizedException("유효하지 않은 인증토큰입니다.");
        }
    }
}
