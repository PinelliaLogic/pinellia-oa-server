package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaJobService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaJob)表控制层
 */
@RestController
@RequestMapping("/job")
public class OaJobController {


    @Autowired
    private OaJobService oaJobService;

    /**
     *  添加
     * @param oaJob
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:add')")
    @PostMapping
    public R add(@RequestBody OaJob oaJob) {
        if (oaJob == null) {
            return R.error(500, "添加失败!");
        }
        //设置创建时间
        oaJob.setCreateTime(now());

        oaJobService.save(oaJob);
        return R.success("成功添加");
    }
    /**
     * 删除
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败");
        }
        OaJob job = oaJobService.getById(id);
        if (job.getCount() != 0) {
            return R.error(500, "删除失败,该岗位员工不为0！");
        }
        oaJobService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     **/
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:delete')")
    public R delete(@RequestBody Long[] ids) {
        int people = 0;
        if (ids == null) {
            return R.error(500, "批量删除失败!");
        }
        for (Long id : ids) {
            OaJob job = oaJobService.getById(id);
            //判断岗位 人数是否为0
            if (job.getCount() != 0) {
                people++;
            }
        }
        if (people == 0) {
            for (Long id : ids) {
                oaJobService.removeById(id);
            }
            return R.success("批量删除成功!");
        }
        return R.error(500, "删除失败,有岗位员工数不为0！");
    }

    /**
     * 修改
     * @param job
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:update')")
    @PutMapping
    public R updateById(@RequestBody OaJob job) {
        if (job == null) {
            return R.error(500, "修改失败");
        }
        //设置更新时间
        UpdateWrapper<OaJob> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", job.getId()).set("update_time", now());
        oaJobService.update(null, wrapper);
        oaJobService.updateById(job);
        return R.success("修改成功");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @param: search
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:list')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        //更新岗位人数
        oaJobService.updateCount();

        LambdaQueryWrapper<OaJob> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaJob::getJobName, search);        //通过工作名称查询
        }

        IPage<OaJob> page = oaJobService.page(new Page<>(pageCount, pageSize), wrapper);
        return R.success("", page);
    }

    /**
     * 通过岗位id查询员工
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('job:list', 'job:query')")
    @GetMapping("/{id}")
    public R getUserByJobId(@PathVariable Long id) {
        List<OaUser> user = oaJobService.getUserByJobId(id);
        return R.success("", user);
    }

    /**
     * 根据岗位ID查询该岗位的员工,分页
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('job:list', 'job:query')")
    @GetMapping("/page/{id}")
    public R getUser(@PathVariable Long id,
                     @RequestParam(defaultValue = "1") Integer pageCount,
                     @RequestParam(defaultValue = "15") Integer pageSize,
                     @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaUser> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaUser::getName, search);        //通过姓名查询
        }

        IPage<OaUser> userList = oaJobService.getUserByJobPage(id, new Page<>(pageCount, pageSize), wrapper);
        return R.success("", userList);
    }

    /**
     * 从岗位中移除员工
     * @param id
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:list')")
    @PostMapping("/remove/{id}")
    public R removeUser(@PathVariable Long id) {
        boolean b = oaJobService.removeUser(id);
        if (b) {
            return R.success("移除成功!");
        }
        return R.error(500, "移除失败！");
    }

    /**
     * 分配员工
     * @param uid
     * @param: did
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('job:list')")
    @PostMapping("/divide/{uid}/{jid}")
    public R divideDepartment(@PathVariable Long uid, @PathVariable Long jid) {
        if (uid != null && jid != null) {
            boolean b = oaJobService.divideUser(jid, uid);
            if (b) {
                return R.success("分配成功!");
            }
        }
        return R.error(500, "操作失败！");
    }
}

