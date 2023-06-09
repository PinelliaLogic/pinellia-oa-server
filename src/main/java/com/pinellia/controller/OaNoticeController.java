package com.pinellia.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaNotice;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaNoticeService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;


/**
 * (OaNotice)表控制层
 */
@RestController
@RequestMapping("/notice")
public class OaNoticeController {

    @Autowired
    private OaNoticeService oaNoticeService;

    /**
     * 添加
     * @param oaNotice
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('notice:add')")
    @PostMapping
    public R add(@RequestBody OaNotice oaNotice) {
        if (oaNotice == null) {
            return R.error(500, "添加失败!");
        }
        //设置创建时间
        oaNotice.setCreateTime(now());

        oaNoticeService.save(oaNotice);
        return R.success("成功添加!");
    }

    /**
     * 删除
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('notice:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败");
        }
        oaNoticeService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 批量删除
     * @param ids
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('notice:delete')")
    @PostMapping("/delete")
    public R deleteBatch(@RequestBody Long[] ids) {
        if (ids == null) {
            return R.error(500, "批量删除失败!");
        }
        boolean b = oaNoticeService.removeByIds(Arrays.asList(ids));
        if (!b) {
            return R.error(500, "批量删除失败!");
        }
        return R.success("批量删除成功!");
    }

    /**
     * 修改
     * @param notice
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('notice:update')")
    @PutMapping
    public R updateById(@RequestBody OaNotice notice) {
        if (notice.getId() == null) {
            return R.error(500, "修改失败");
        }
        boolean b = oaNoticeService.updateById(notice);
        //设置更新时间
        UpdateWrapper<OaNotice> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", notice.getId()).set("update_time", now());
        oaNoticeService.update(null, wrapper);
        if (!b) {
            return R.error(500, "修改失败");
        }
        return R.success("修改成功");
    }

    /**
     * 查询所有公告
     * @param
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getNoticeAll")
    public R getNoticeAll() {
        //查询已发布的公告
        UpdateWrapper<OaNotice> wrapper = new UpdateWrapper<>();
        wrapper.eq("status", "1");
        List<OaNotice> notices = oaNoticeService.list(wrapper);
        return R.success("", notices);
    }

    /**
     * 分页
     * @param pageNum
     * @param: pageSize
     * @param: search
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('notice:list', 'notice:query')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<OaNotice> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaNotice::getTitle, search);        //通过标题查询
        }
        IPage<OaNotice> page = oaNoticeService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success("", page);
    }

    /**
     * 更新公告状态
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('notice:update')")
    @PostMapping("/{id}")
    public R updateStatusById(@PathVariable Long id) {
        if (id != null) {
            OaNotice notice = oaNoticeService.getById(id);
            if (notice.getStatus().equals('0')) {
                UpdateWrapper<OaNotice> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", id).set("status", "1");
                oaNoticeService.update(null, wrapper);
                return R.success("发布成功!");
            }
            UpdateWrapper<OaNotice> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", id).set("status", "0");
            oaNoticeService.update(null, wrapper);
            return R.success("撤销成功!");
        }
        return R.error(500, "操作失败!");
    }


}

