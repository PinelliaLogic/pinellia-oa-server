package com.pinellia.controller.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaUserService;
import com.pinellia.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/avatar")
public class AvatarController {

    @Autowired
    private OaUserService oaUserService;

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";


    private static final String rootFilePath =
            System.getProperty("user.dir") + "/src/main/resources/static/avatar/";

    /**
     * 头像上传
     *
     * @param file
     * @return
     **/
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public R uploadAvatar(MultipartFile file) throws IOException {
        // 允许上传的文件类型
        List<String> allowedTypes = Arrays.asList(
                "image/jpeg",
                "image/png"
        );
        // 校验文件类型
        String contentType = file.getContentType();
        if (!allowedTypes.contains(contentType)) {
            return R.error(500, "只能上传jpg和png格式的头像！");
        }
        //生成文件标识
        String flag = IdUtil.fastSimpleUUID();
        //获取文件存储到后端的路径
        String filePath = rootFilePath + flag + "_" + file.getOriginalFilename();
        //使用hutool工具将文件写入到存储路径
        FileUtil.writeBytes(file.getBytes(), filePath);
        return R.success(ip + ":" + port + "/avatar/" + flag + "_" + file.getOriginalFilename());     //返回url
    }

    /**
     * 获取头像
     *
     * @param fileName
     * @return
     **/
    @GetMapping("/{fileName}")
    public void getAvatar(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        OutputStream os = null;
        //获取所有文件名称
        List<String> fileNames = FileUtil.listFileNames(rootFilePath);
        //找到跟参数一致的文件
        String file = fileNames.stream().filter(name -> name.contains(fileName)).findAny().orElse("");
        try {
            if (StrUtil.isNotEmpty(file)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(rootFilePath + file);
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "头像不存在");
            }
        } catch (Exception e) {
            log.error("获取头像失败", e);
        }
    }

    /**
     * 修改头像
     * @param id
     * @param: avatarPath
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}")
    public R updateAvatar(@PathVariable Long id, @RequestBody String avatarPath) {
        //获取用户的原头像路径
        OaUser user = oaUserService.getById(id);
        String path = user.getAvatar();
        if (path == null || path.equals("")) {
            UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
            String temp = avatarPath.replaceAll("\"", "");
            wrapper.eq("id", id).set("avatar", temp);
            oaUserService.update(null, wrapper);
            return R.success("修改成功!");
        }
        // 截取文件名
        String avatar = path.substring(path.lastIndexOf("/") + 1);
        //拼接文件的绝对路劲
        File filePath = new File(rootFilePath + avatar);
        //删除原来的图片
        boolean delete = filePath.delete();
        if (delete) {
            //修改头像
            UpdateWrapper<OaUser> wrapper = new UpdateWrapper<>();
            String temp = avatarPath.replaceAll("\"", "");
            wrapper.eq("id", id).set("avatar", temp);
            oaUserService.update(null, wrapper);
            return R.success("修改成功!");
        }
        return R.error(500, "修改失败!");
    }

}
