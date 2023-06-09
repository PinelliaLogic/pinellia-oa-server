package com.pinellia.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Map;

/**
 * 统一返回结果
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class R implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static R success(int code, String msg, Object data) {
        R r = new R();
        r.setCode(200);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R success() {
        return success(200, null, null);
    }

    public static R success(String msg) {
        return success(200, msg, null);
    }

    public static R success(String msg, Object data) {
        return success(200, msg, data);
    }

    public static R success(String msg ,Map<String, Object> data) {
        return success(200, msg, data);
    }

    public static R error(int code, String msg, Object data) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static R error(int code) {
        return error(code, null, null);
    }

    public static R error(int code, String msg) {
        return error(code, msg, null);
    }

    public static R error() {
        return error(404, null, null);
    }

    //对response写入Object数据
    public static void responseOutUser(HttpServletResponse response, int statusCode , Object result) {
        ObjectMapper mapper = new ObjectMapper();
        PrintWriter writer = null;
        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        try {
            writer = response.getWriter();
            mapper.writeValue(writer, result);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
