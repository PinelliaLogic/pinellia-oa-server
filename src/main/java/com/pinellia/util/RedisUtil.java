package com.pinellia.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static StringRedisTemplate stringRedisTemplateStatic;

    @PostConstruct //在项目启动的时候执行该方法,也可以理解为在spring容器初始化的时候执行该方法。
    public void initStringRedisTemplate() {
        stringRedisTemplateStatic = this.stringRedisTemplate;
    }

    private static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /*
     * 保存token信息到redis,也可直接在创建token中使用该方法
     * */
    public static void SaveTokenInfo(String token, String username) {
        //以username做key
        LocalDateTime localDateTime = LocalDateTime.now();
        stringRedisTemplateStatic.opsForHash().put(username, "token", JwtUtil.prefix + token);
        stringRedisTemplateStatic.opsForHash().put(username, "expiration",
                df.format(localDateTime.plus(3600 * 1000, ChronoUnit.MILLIS)));      //过期时间1小时

        stringRedisTemplateStatic.expire(username, 7 * 24 * 60 * 60 * 1000, TimeUnit.SECONDS);    //在redis中的保存时间为7天
    }

    /*
     * 检查redis是否存在token
     * */
    public static boolean hasToken(String username) {
        return Boolean.TRUE.equals(stringRedisTemplateStatic.opsForHash().getOperations().hasKey(username));
    }

    /*
     * 清楚redis中存储的信息
     **/
    public static void deleteToken(String username) {
        stringRedisTemplateStatic.delete(username);
    }
}
