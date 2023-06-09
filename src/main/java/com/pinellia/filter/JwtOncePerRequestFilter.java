package com.pinellia.filter;

import com.pinellia.common.security.service.UserDetailServiceImpl;
import com.pinellia.util.JwtUtil;
import com.pinellia.util.RedisUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 过滤器,在请求过来的时候,解析请求头中的token,再解析token得到用户信息,再存到SecurityContextHolder中
 */
@Slf4j
@Component
public class JwtOncePerRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 从请求头中获取 token
        final String authorizationHeader = request.getHeader(JwtUtil.header);

        String username = null;
        String jwt = null;

        // 判断 token 是否有效
        if (authorizationHeader != null && authorizationHeader.startsWith(JwtUtil.prefix)) {
            jwt = authorizationHeader.substring(7);     //获取到前缀后的token
//            log.error(jwt);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 获取用户信息
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 验证 token 是否有效
            if (jwtUtil.validateToken(jwt, userDetails)) {

                // 获取 Redis 中保存的 Token 有效期
                String redisTokenExpiration = redisTemplate.opsForHash().get(username, "expiration").toString();

                // 验证 Redis 中保存的 Token 有效期与 JWT 中的是否相等
                String tokenExpiration = jwtUtil.extractExpiration(jwt).toString();

                if (!redisTokenExpiration.equals(tokenExpiration)) {

                    // 生成新的 Token 并存入 Redis 中
                    String newToken = jwtUtil.generateToken(userDetails.getUsername());
                    RedisUtil.SaveTokenInfo(username, newToken);

                    // 更新响应头中的 Token
                    response.setHeader(JwtUtil.header, JwtUtil.prefix + newToken);
                } else {

                    // Token 已过期，需要重新登陆
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token 过期");
                }
            } else {
                // 验证通过，将用户信息存入 SecurityContext 中
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 更新响应头中的 Token
                response.setHeader(JwtUtil.header, JwtUtil.prefix + jwt);
            }
        }

        chain.doFilter(request, response);
    }
}

