package com.pinellia.common.exception;

import cn.hutool.jwt.JWTException;

public class JwtException extends JWTException {
    public JwtException(Throwable e) {
        super(e);
    }

    public JwtException(String message) {
        super(message);
    }

    public JwtException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public JwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtException(String message, Throwable throwable, boolean enableSuppression, boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }

    public JwtException(Throwable throwable, String messageTemplate, Object... params) {
        super(throwable, messageTemplate, params);
    }
}
