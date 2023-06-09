package com.pinellia.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义用户禁用异常处理
 **/
public class UserLockException extends AuthenticationException {
    public UserLockException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserLockException(String msg) {
        super(msg);
    }
}
