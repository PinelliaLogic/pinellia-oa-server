package com.pinellia.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static final String header = "Authorization";        //请求头
    private static final String secret = "pinellia";      //签名

    public static final String prefix = "Bearer ";      //token前缀

    //创建token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();   // 创建一个空的 Map 对象 claims，用于存储 payload 中自定义的键值对信息
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expirationDate = new Date(nowMillis + (3600000 * 8));       //过期时间为8小时
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)    //主题
                .setIssuedAt(now)       //设置当前时间为签发时间
                .setExpiration(expirationDate)  //过期时间1小时
                .signWith(SignatureAlgorithm.HS256, secret)     //HS256算法加密
                .compact();
        return token;
    }

    //验证当前token是否有效
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);     //从token中提取用户名
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));  //验证是否有效，用户信息是否匹配
    }

    //判断token是否过期
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);      //提取过期时间
        return expiration.before(new Date());       //判断过期时间是否在当前时间之前
    }

    //提取用户名
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //提取过期时间
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //解析部分属性
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //解析全部属性
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
