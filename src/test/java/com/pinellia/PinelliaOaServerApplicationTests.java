package com.pinellia;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.common.security.service.UserDetailServiceImpl;
import com.pinellia.dao.OaUserDao;
import com.pinellia.dao.vo.OaClockNotesVoDao;
import com.pinellia.entity.*;
import com.pinellia.entity.vo.OaClockNotesVo;
import com.pinellia.entity.vo.OaDepartmentVo;
import com.pinellia.entity.vo.OaUserVo;
import com.pinellia.service.*;
import com.pinellia.service.vo.OaClockNotesVoService;
import com.pinellia.service.vo.OaUserVoService;
import com.pinellia.util.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Wrapper;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;


@SpringBootTest
class PinelliaOaServerApplicationTests {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private OaDepartmentService oaDepartmentService;

    @Autowired
    private OaUserDao oaUserDao;

    @Autowired
    private OaUserService oaUserService;

    @Autowired
    private OaRoleService oaRoleService;

    @Autowired
    private OaUserVoService oaUserVoService;

    @Autowired
    private OaClockNotesVoService oaClockNotesVoService;

    @Autowired
    private OaClockNotesVoDao oaClockNotesVoDao;

    @Autowired
    private OaRequestService oaRequestService;

    @Autowired
    private OaJobService oaJobService;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

    @Test
    void Test() {
        IPage<OaUserVo> userPage = oaUserVoService.pages(1, 15, "");
        System.out.println(userPage.getTotal());
    }

    @Test
    void Test2() {
        List<OaClockNotesVo> clock = oaClockNotesVoService.getClock(1L);
        System.out.println(clock);
    }

    @Test
    void Test3() {
        String jwt = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaW5lbGxpYSIsImV4cCI6MTY4MTEzNzgzOSwiaWF0IjoxNjgxMTM0MjM5fQ.-V0XcQfUhYS8k6my07Q5DTg8on-ga1mGDN8q8j4YvCQ";
        String substring = jwt.substring(7);
        System.out.println(substring);
    }

    @Test
    void Test4() {
        OaUser user = oaUserService.getById(100002);
        user.setStatus("1");
    }

    @Test
    void Test5() {
        List<OaRole> list = oaRoleService.list();
        for (OaRole oaRole : list) {
            System.out.println(oaRole);
        }
    }

    @Test
    void Test6() {
        IPage<OaDepartment> page = oaDepartmentService.page(new Page<>(1, 5), null);
    }

    @Test
    void Test7() {
        List<OaClockNotesVo> clock = oaClockNotesVoService.getClock(5L);
        for (OaClockNotesVo oaClockNotesVo : clock) {
            System.out.println(oaClockNotesVo);
        }
    }

    @Test
    void Test8() {
        LambdaQueryWrapper<OaUser> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(OaUser::getId, 100001);
        List<OaUser> list = oaUserService.list(wrapper);
        for (OaUser user : list) {
            System.out.println("时间" + user.getCreateTime());
        }
    }

    @Test
    void Test9() {
        OaRequest request = oaRequestService.getById(1L);

        System.out.println(request);

        UpdateWrapper<OaRequest> wrapper = new UpdateWrapper<>();
        //0 未申请 1已撤回
        if (request.getStatus() >= 1) {
            wrapper.eq("id", 1L).set("status", 2).set("time", now());
        } else {
            wrapper.eq("id", 1L).set("status", 1);
        }
        oaRequestService.update(null, wrapper);
    }

    @Test
    void Test10() {
        oaJobService.getUserByJobPage(300L, new Page<>(1, 5), null);
    }

    @Test
    void Test11() {
        List<OaDepartment> list = oaDepartmentService.list();

        List<OaDepartmentVo> oaDepartmentVos = oaDepartmentService.departmentTree(list);
        System.out.println(oaDepartmentVos);
    }

    @Test
    void Test12() {
        String rootFilePath =
                System.getProperty("user.dir") + "\\src\\main\\resources\\files\\";
        System.out.println(rootFilePath);
    }
}
