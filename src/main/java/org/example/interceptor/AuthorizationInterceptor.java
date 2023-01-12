package org.example.interceptor;

import lombok.RequiredArgsConstructor;
import org.example.service.JwtService;
import org.example.util.exception.UnauthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = request.getHeader("jwtToken");
        if (token != null && token.length() > 0) {
            jwtService.checkValid(token); // 토큰 유효성 검증
            return true;
        } else { // 유효한 인증토큰이 아닐 경우
            throw new UnauthorizedException("유효한 인증토큰이 존재하지 않습니다.");
        }
    }
}
