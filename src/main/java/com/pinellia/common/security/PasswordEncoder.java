package com.pinellia.common.security;

import com.pinellia.util.RsaUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 后端使用BCrypt加密
 **/
@Slf4j
@NoArgsConstructor
public class PasswordEncoder extends BCryptPasswordEncoder {

    private static final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDRB29gN4JCqIDsWtRAWrqD6NT1i/XUfRpQKFyzWUiz+gth4OXWyUgOn5lB9AD/HyycIPmT0RDy+JsjEs6VIYf5C159dZr+Nk9BexmDvVFkyI2fpGIzgfpuuAk/38Rni/MGU/n0cNN5ElJPsEGy0R5oGESJ0iVZmbCrXggNiYKvWW0DWE4pycskJHL0ndmiRJ6Xz0nl36is0krhyD7fTfZw8GhCtMIanldoVV+VpFEVPkXUsef40Y36LampnGP3JSgOwSB/46nCbj+GZX42b5QaRWydYDgvWHhTjqLzaifNAu8EEOb9BHMRsBmO4SKQuGVpAb7vS9OZ15PJj0YPYM8LAgMBAAECggEBAKR9ulq4M+yq15GnxIHEJCSr8/IrKtAFhtBvgsusRjdDXPOwXI5SpT7Ev4EUODiL2roehOiazEC3/LV0FldrJKqKFNib1NWZ/XKlIaX3NHcgD7sCTDkGXwIEF1tJhzCJBD0FlpySsQgEr/t/06OZRtlZx43Bbpz9AfdJLwMgJFRBmFpPbenlggGvF1bIDZdMi+VdL/lKvhV91cpi83/H1Nky0k0XdGOqCXhrxVlt8iLtrIwxFUjiqusTgBFuAMzjVOmcmr+gbmYnHCS67ZNKV9J/xzkNP4OnekmO6nf2G9imLmVw1L/jD5pwcpwyFU066NV8R8Y2XwDNR4Z9poM4idECgYEA/1H9qxyf2RM7xrv77FN8AH7OkWauzgsjCgZ6CQq7Ikvbu2BSdS3R6fNzXM4Fz3hsPLu4IhrbQhz3kmOOHc1w8VbDpNBpaSgudfbcCEYGAxal7Nzb4Gjr+Rt573h6qmwjHhP7KSwlNXm3c1yN7k+aAMuoH/d1OMbF9Ta+3/Jv/fMCgYEA0ZXlKqWe/kvGSH0Jh8VMsH588ar+Pu9HmiAOUj/qNsQnTp3THb+C5wxoY1ceBkRNIF0duT/GVjM4++7lN49Rah1UTcig4DeAQtuaOh5q6T3/8BbnvvGorIEEu3e3UKFwVAE/rhO0wfT0IcIZYL5ilLjiSLgP69BIX7exyrX4eIkCgYBdafgvpNdvKrRSL/EqNBlIbRzS4gjO5hA52MjJih2RnMOWONWyfcMxn5rzt4U0tVCUn/HsuHrEI6kI7FfQ25uZES+fm4kVqlflXR6AAG6urpiiouPnpTvA9qaUUTZAKDRpCtJ36+jpOdE+6fwb1w/3fovStk0zKra+0vrfIOqMtQKBgQC8zUmad4SZFG3VEz6onmYNgITRt/Y9f5SDFWmRUp6NI3s9aROr8r/bn+PRycL0ZkcMZpXGMdtb/9zPp0+6E1OGQlGVmKzED3ttvyJaRNiOsRmEkT3HVy9Z5wf6UUhETIpCQrW4/WuSZjYeyxnNevSXgU3nsFg3WHQ6NeLl97OT8QKBgGciwwJ9SMCWogUU6qewEMSvexTC/BeTKs1HQQS9oGxqYR6gAvYfN739Y17QGoGTLYVk6mFvgMwxZyh+MUYRpBRenWFICPAJHGb82fHnDbfGdk790H6VrRlqBXZMFhF2QIFeu065FkXzOpabqSrClsuC/Z4rSdq5dQre+qnK9JEL";

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // 接收到的前端的密码
        String pwd = rawPassword.toString();
//        log.error("PasswordEncoder解密前=====>" + pwd);
        // 进行rsa解密
        try {
            //私钥解密
            pwd = RsaUtil.decrypt(pwd, privateKey);
//            log.error("解密的密码===》" + pwd);
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if (encodedPassword != null && encodedPassword.length() != 0) {
            return BCrypt.checkpw(pwd, encodedPassword);
        } else {
            return false;
        }
    }
}

