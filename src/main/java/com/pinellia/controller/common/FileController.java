package com.pinellia.controller.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.pinellia.entity.OaResources;
import com.pinellia.service.OaResourcesService;
import com.pinellia.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private OaResourcesService oaResourcesService;

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    private static final String rootFilePath =
            System.getProperty("user.dir") + "/src/main/resources/static/files/";

    /**
     * 文件上传
     *
     * @param file
     * @return
     **/
    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        // 允许上传的文件类型
        List<String> allowedTypes = Arrays.asList(
                "application/msword",
                "application/vnd.ms-powerpoint",
                "application/pdf",
                "text/plain",
                "application/zip",
                "application/x-rar-compressed"
        );
        // 校验文件类型
        String contentType = file.getContentType();
        if (!allowedTypes.contains(contentType)) {
            return R.error(500, "只能上传 Word、PPT、pdf、txt、zip 和 rar 文件");
        }
        // 生成文件标识
        String flag = IdUtil.fastSimpleUUID();
        // 设置文件存储的绝对路径
        String filePath = rootFilePath + flag + "_" + file.getOriginalFilename();
        // 使用 hutool 工具将文件写入到存储路径
        FileUtil.writeBytes(file.getBytes(), filePath);
        return R.success(ip + ":" + port + "/files/" + flag + "_" + file.getOriginalFilename());     // 返回URL
    }

    /**
     * 文件下载
     *
     * @param flag, response
     * @return
     **/
    @GetMapping("/{flag}/{id}")
    public void download(@PathVariable String flag, @PathVariable Long id, HttpServletResponse response) {
        OaResources resources = oaResourcesService.getById(id);
        if (resources.getStatus() == '1') {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);       //响应403，无权限
            return;
        }
        OutputStream os = null;
        //获取所有文件名称
        List<String> fileNames = FileUtil.listFileNames(rootFilePath);
        //找到跟参数一致的文件
        String file = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(file)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(rootFilePath + file);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件不存在");
            }
        } catch (Exception e) {
            log.error("文件下载失败", e);
        }
    }
}
