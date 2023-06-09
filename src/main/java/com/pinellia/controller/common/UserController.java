package com.pinellia.controller.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.common.UpdatePassword;
import com.pinellia.entity.common.UpdateUserInfo;
import com.pinellia.service.OaUserService;
import com.pinellia.service.common.QueryPasswordService;
import com.pinellia.service.common.UpdateUserInfoService;
import com.pinellia.util.R;
import com.pinellia.util.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static cn.hutool.core.date.DateTime.now;


@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserController {

    @Autowired
    private UpdateUserInfoService updateUserInfoService;

    @Autowired
    private QueryPasswordService queryPasswordService;

    @Autowired
    private OaUserService oaUserService;

    private static final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRB29gN4JCqIDsWtRAWrqD6NT1i/XUfRpQKFyzWUiz+gth4OXWyUgOn5lB9AD/HyycIPmT0RDy+JsjEs6VIYf5C159dZr+Nk9BexmDvVFkyI2fpGIzgfpuuAk/38Rni/MGU/n0cNN5ElJPsEGy0R5oGESJ0iVZmbCrXggNiYKvWW0DWE4pycskJHL0ndmiRJ6Xz0nl36is0krhyD7fTfZw8GhCtMIanldoVV+VpFEVPkXUsef40Y36LampnGP3JSgOwSB/46nCbj+GZX42b5QaRWydYDgvWHhTjqLzaifNAu8EEOb9BHMRsBmO4SKQuGVpAb7vS9OZ15PJj0YPYM8LAgMBAAECggEBAKR9ulq4M+yq15GnxIHEJCSr8/IrKtAFhtBvgsusRjdDXPOwXI5SpT7Ev4EUODiL2roehOiazEC3/LV0FldrJKqKFNib1NWZ/XKlIaX3NHcgD7sCTDkGXwIEF1tJhzCJBD0FlpySsQgEr/t/06OZRtlZx43Bbpz9AfdJLwMgJFRBmFpPbenlggGvF1bIDZdMi+VdL/lKvhV91cpi83/H1Nky0k0XdGOqCXhrxVlt8iLtrIwxFUjiqusTgBFuAMzjVOmcmr+gbmYnHCS67ZNKV9J/xzkNP4OnekmO6nf2G9imLmVw1L/jD5pwcpwyFU066NV8R8Y2XwDNR4Z9poM4idECgYEA/1H9qxyf2RM7xrv77FN8AH7OkWauzgsjCgZ6CQq7Ikvbu2BSdS3R6fNzXM4Fz3hsPLu4IhrbQhz3kmOOHc1w8VbDpNBpaSgudfbcCEYGAxal7Nzb4Gjr+Rt573h6qmwjHhP7KSwlNXm3c1yN7k+aAMuoH/d1OMbF9Ta+3/Jv/fMCgYEA0ZXlKqWe/kvGSH0Jh8VMsH588ar+Pu9HmiAOUj/qNsQnTp3THb+C5wxoY1ceBkRNIF0duT/GVjM4++7lN49Rah1UTcig4DeAQtuaOh5q6T3/8BbnvvGorIEEu3e3UKFwVAE/rhO0wfT0IcIZYL5ilLjiSLgP69BIX7exyrX4eIkCgYBdafgvpNdvKrRSL/EqNBlIbRzS4gjO5hA52MjJih2RnMOWONWyfcMxn5rzt4U0tVCUn/HsuHrEI6kI7FfQ25uZES+fm4kVqlflXR6AAG6urpiiouPnpTvA9qaUUTZAKDRpCtJ36+jpOdE+6fwb1w/3fovStk0zKra+0vrfIOqMtQKBgQC8zUmad4SZFG3VEz6onmYNgITRt/Y9f5SDFWmRUp6NI3s9aROr8r/bn+PRycL0ZkcMZpXGMdtb/9zPp0+6E1OGQlGVmKzED3ttvyJaRNiOsRmEkT3HVy9Z5wf6UUhETIpCQrW4/WuSZjYeyxnNevSXgU3nsFg3WHQ6NeLl97OT8QKBgGciwwJ9SMCWogUU6qewEMSvexTC/BeTKs1HQQS9oGxqYR6gAvYfN739Y17QGoGTLYVk6mFvgMwxZyh+MUYRpBRenWFICPAJHGb82fHnDbfGdk790H6VrRlqBXZMFhF2QIFeu065FkXzOpabqSrClsuC/Z4rSdq5dQre+qnK9JEL";

    static int count = 3;

    static Long time = 0L;

    /**
     * 修改手机号和邮箱
     *
     * @param id, updateUserInfo
     * @return
     **/
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{id}")
    public R updateInfo(@PathVariable Long id, @RequestBody UpdateUserInfo updateUserInfo) {
        boolean b = updateUserInfoService.updateUserInfo(updateUserInfo.getPhone(), updateUserInfo.getEmail(), id);
        if (!b) {
            return R.error(500, "操作失败!");
        }
        //设置修改时间
        UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("update_time", now());
        oaUserService.update(null, wrapper);
        return R.success("操作成功!");
    }

    /**
     * 修改密码
     *
     * @param id, updatePassword
     * @return
     **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}")
    public R updatePassword(@PathVariable Long id, @RequestBody UpdatePassword updatePassword) {
        if (time > System.currentTimeMillis()) {
            return R.error(500, "请稍后再尝试!");
        }
        String password = queryPasswordService.getPasswordById(id);     //查询密码
        //解密
        String pwd = RsaUtil.decrypt(updatePassword.getOldPassword(), privateKey);

        String newPwd = RsaUtil.decrypt(updatePassword.getNewPassword(), privateKey);

        if (!BCrypt.checkpw(pwd, password)) {
            //输错3次锁定用户
            if (count == 1) {
                time = System.currentTimeMillis() + 60 * 60 * 1000;        //一小时后才能进行修改密码操作
                return R.error(500, "请稍后再尝试!");
            }
            count--;
            return R.error(500, "旧密码输入错误！还有" + count + "次机会!");
        }
        if(BCrypt.checkpw(newPwd, password)) {
            return R.error(500, "新密码不能与原密码一致！");
        }
        boolean b = queryPasswordService.updatePasswordById(new BCryptPasswordEncoder().encode(newPwd), id);
        if (b) {
            //设置修改时间
            UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id).set("update_time", now());
            oaUserService.update(null, wrapper);
            return R.success("操作成功!");
        }
        return R.error(500, "操作失败!");
    }

}
