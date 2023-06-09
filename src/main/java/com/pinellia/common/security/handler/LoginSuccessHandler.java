package com.pinellia.common.security.handler;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRole;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.vo.OaUserVo;
import com.pinellia.service.OaMenuService;
import com.pinellia.service.OaRoleService;
import com.pinellia.service.OaUserService;
import com.pinellia.service.vo.OaUserVoService;
import com.pinellia.util.JwtUtil;
import com.pinellia.util.R;
import com.pinellia.util.RedisUtil;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cn.hutool.core.date.DateTime.now;

/**
 * 登录成功处理器
 **/
@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private OaUserService oaUserService;

    @Autowired
    private OaUserVoService oaUserVoService;

    @Autowired
    private OaMenuService oaMenuService;

    @Autowired
    private OaRoleService oaRoleService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
//        log.error("调用了============>LoginSuccessHandler");
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();

        String username = authentication.getName();
        //生成jwt
        String token = jwtUtil.generateToken(username);

        //redis缓存token
        RedisUtil.SaveTokenInfo(token, username);

        // JWT放置到请求头中
        response.setHeader(JwtUtil.header, token);

        //获取用户信息
        OaUser user = oaUserService.loadUserByUsername(username);
        //获取完整的用户信息
        OaUserVo userInfo = oaUserVoService.getUserById(user.getId());

        //获取角色
        List<OaRole> roleList = oaRoleService.getRoleList(userInfo.getId());
        //获取菜单权限
        //遍历所有的角色，获取所有菜单权限且不重复
        Set<OaMenu> menuSet = new HashSet<>();
        for (OaRole oaRole : roleList) {
            //获取菜单
            List<OaMenu> menuList = oaMenuService.getMenu(oaRole.getId());
            menuSet.addAll(menuList);
        }
        ArrayList<OaMenu> menu = new ArrayList<>(menuSet);

        //排序
        menu.sort(Comparator.comparing(OaMenu::getOrderNum));
        //转成菜单树
        List<OaMenu> menuList = oaMenuService.menuTree(menu);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("userInfo", userInfo);
        map.put("menuList", menuList);

        //设置登录时间
        UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", user.getId()).set("login_date", now());
        oaUserService.update(null, wrapper);

        outputStream.write(JSONUtil.toJsonStr(R.success("登录成功!", map)).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}

