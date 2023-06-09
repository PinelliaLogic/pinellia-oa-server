package com.pinellia.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.pinellia.util.JwtUtil;
import com.pinellia.util.R;
import com.pinellia.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义退出登录处理
 **/
@Slf4j
@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String username = null;
        if (authentication != null) { // 非空判断
            username = ((UserDetails)authentication.getPrincipal()).getUsername();

            //清除redis中的信息
            if (RedisUtil.hasToken(username)) {
                RedisUtil.deleteToken(username);
            }
        }
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        //清空请求头
        response.setHeader(JwtUtil.header, "");

        outputStream.write(JSONUtil.toJsonStr(R.success("退出成功!")).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

