package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaResources;
import com.pinellia.service.OaDepartmentService;
import com.pinellia.service.OaResourcesService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaResources)表控制层
 */
@RestController
@RequestMapping("/resources")
public class OaResourcesController {

    @Autowired
    private OaResourcesService oaResourcesService;

    @Autowired
    private OaDepartmentService oaDepartmentService;

    private static final String rootFilePath =
            System.getProperty("user.dir") + "/src/main/resources/static/files/";

    /**
     * 添加资源信息
     *
     * @param resources
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('resources:add')")
    @PostMapping("/add/{id}")
    public R add(@PathVariable Long id, @RequestBody OaResources resources) {
        if (resources == null) {
            return R.error(500, "输入字段不为空!");
        }
        //设置创建时间
        resources.setCreateTime(now());

        String path = resources.getCover();
        String resourcesName = path.substring(path.lastIndexOf("/") + 1);
        //截取文件后缀名，设置其类型
        String cover = StringUtils.getFilenameExtension(resourcesName);
        resources.setType(cover);

        //添加资源到oa_resources
        oaResourcesService.save(resources);

        //添加到关联表
        oaResourcesService.addResources(id, resources.getId());

        return R.success("成功添加");
    }

    /**
     * 删除
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('resources:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败!");
        }
        String path = oaResourcesService.getById(id).getCover();
        if (path != null) {
            String resources = path.substring(path.lastIndexOf("/") + 1);
            //拼接文件的绝对路劲
            File filePath = new File(rootFilePath + resources);
            //删除原来的文件
            filePath.delete();
        }

        boolean b = oaResourcesService.removeById(id);
        //从关联表中删除
        boolean b1 = oaResourcesService.deleteResourcesById(id);
        if (b && b1) {
            return R.success("删除成功");
        }
        return R.error(501, "系统错误!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('resources:delete')")
    @PostMapping("/delete")
    public R deleteBatch(@RequestBody Long[] ids) {
        if (ids == null) {
            return R.error(500, "批量删除失败!");
        }
        for (Long id : ids) {
            String path = oaResourcesService.getById(id).getCover();
            if (path != null) {
                String resources = path.substring(path.lastIndexOf("/") + 1);
                //拼接文件的绝对路劲
                File filePath = new File(rootFilePath + resources);
                //删除原来的文件
                filePath.delete();
            }
            oaResourcesService.removeById(id);
            oaResourcesService.deleteResourcesById(id);
        }
        return R.success("批量删除成功!");
    }

    /**
     * 修改
     *
     * @param resources
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('resources:update')")
    @PutMapping
    public R updateById(@RequestBody OaResources resources) {
        if (resources.getId() == null || resources.getCover() == null) {
            return R.error(500, "修改失败");
        }
        String path = resources.getCover();
        String resourcesName = path.substring(path.lastIndexOf("/") + 1);
        //拼接文件的绝对路径
        File filePath = new File(rootFilePath + resourcesName);
        //文件大小
        long fileSize = filePath.length() / 1024 / 1024;        //单位MB
        resources.setSize(fileSize + "");
        //截取文件后缀名
        String cover = StringUtils.getFilenameExtension(resourcesName);
        resources.setType(cover);

        //删除原来的文件
        boolean delete = true;
        String oldFile = oaResourcesService.getById(resources.getId()).getCover();
        if (oldFile != null) {
            File oldFilePath = new File(rootFilePath + oldFile.substring(oldFile.lastIndexOf("/") + 1));
            if (oldFilePath.exists()) { //检查原始文件是否存在
                delete = oldFilePath.delete();  //删除原来的文件
            }
        }
        if (delete) {  //删除成功，更新资源信息
            oaResourcesService.updateById(resources);
            return R.success("修改成功");
        } else {  //删除失败
            return R.error(500, "修改失败");
        }
    }

    /**
     * 分页
     *
     * @param pageNum
     * @return com.pinellia.util.R
     * @param: pageSize
     * @param: search
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('resources:list', 'resources:query')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<OaResources> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaResources::getResourcesName, search);        //通过名称查询
        }
        IPage<OaResources> page = oaResourcesService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success("", page);
    }

    /**
     * 修改资源的状态
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('resources:list')")
    @PostMapping("/{id}")
    public R updateStatus(@PathVariable Long id) {
        OaResources resources = oaResourcesService.getById(id);
        if (resources.getStatus() == '0') {
            UpdateWrapper<OaResources> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", resources.getId()).set("status", "1");
            oaResourcesService.update(null, wrapper);
            return R.success("成功");
        }
        UpdateWrapper<OaResources> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", resources.getId()).set("status", "0");
        oaResourcesService.update(null, wrapper);
        return R.success("成功");
    }

    /**
     * 获取可下载的资源
     *
     * @param
     * @return com.pinellia.util.R
     */
    @GetMapping("/getAll/{id}")
    @PreAuthorize("isAuthenticated()")
    public R getAllDocument(@PathVariable Long id) {
        List<OaResources> resultList = oaResourcesService.getAll(id);
        return R.success("", resultList);
    }

    /**
     * 用户可查询的资料分页
     *
     * @param pageNum
     * @return com.pinellia.util.R
     * @param: pageSize
     * @param: search
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public R myPage(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<OaResources> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaResources::getResourcesName, search);        //通过名称查询
        }
        IPage<OaResources> page = oaResourcesService.selectMyPage(id, new Page<>(pageNum, pageSize), wrapper);
        return R.success("", page);
    }

    /**
     * 获取资源拥有者的信息
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('resources:list', 'resources:query')")
    @GetMapping("/tree/{id}")
    public R getDivideUser(@PathVariable Long id) {
        return R.success("", oaResourcesService.getDivideUser(id));
    }

    /**
     * 获取部门-员工树
     *
     * @param
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('resources:list', 'resources:query')")
    @GetMapping("/tree")
    public R getDepartmentUser() {
        return R.success("", oaDepartmentService.departmentTree(oaDepartmentService.list()));
    }

    /**
     * 分配资源
     *
     * @param rid
     * @return com.pinellia.util.R
     * @param: ids
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('resources:list')")
    @PostMapping("/divide/{rid}")
    public R divideUserResourcesById(@PathVariable Long rid, @RequestBody List<Long> ids) {
        if (rid == null) {
            return R.error(500, "分配失败!");
        }
        //先删除该资源拥有者
        oaResourcesService.removeResourcesById(rid);
        //分配
        for (Long id : ids) {
            oaResourcesService.divideResources(id, rid);
        }
        return R.success("分配成功!");
    }


}

