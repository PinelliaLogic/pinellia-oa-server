package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaCalendar;
import com.pinellia.service.OaCalendarService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaCalendar)表控制层
 *
 * @author pinellia
 * @since 2023-04-11 17:06:55
 */
@RestController
@RequestMapping("/calendar")
public class OaCalendarController {
    /**
     * 服务对象
     */
    @Autowired
    private OaCalendarService oaCalendarService;

    /**
     * 添加事件
     *
     * @param oaCalendar
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('calendar:add')")
    @PostMapping
    public R add(@RequestBody OaCalendar oaCalendar) {
        if (oaCalendar == null) {
            return R.error(500, "添加失败!");
        }
        //设置创建时间
        oaCalendar.setCreateTime(now());
        oaCalendarService.save(oaCalendar);
        return R.success("成功添加!");
    }

    /**
     * 查询进行中的和未开始所有事件
     * @param
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('calendar:list', 'calendar:query', 'user:calendar')")
    @GetMapping("/getAll")
    public R getRoleAll() {
        //获取日程结束时间，大于当前时间的
        UpdateWrapper<OaCalendar> wrapper = new UpdateWrapper<>();
        wrapper.gt("end_time", now());
        List<OaCalendar> list = oaCalendarService.list(wrapper);
        return R.success("", list);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     **/
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('calendar:delete')")
    public R delete(@RequestBody Long[] ids) {
        boolean b = oaCalendarService.removeByIds(Arrays.asList(ids));
        if (b) {
            return R.success("批量删除成功!");
        }
        return R.error(500, "批量删除失败!");
    }

    /**
     * 修改
     *
     * @param calendar
     * @return
     **/
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('calendar:update')")
    @PutMapping
    public R updateById(@RequestBody OaCalendar calendar) {
        if (calendar == null) {
            return R.error(500, "修改失败");
        }
        boolean b = oaCalendarService.updateById(calendar);
        if (b) {
            //设置修改时间
            UpdateWrapper<OaCalendar> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", calendar.getId()).set("update_time", now());
            oaCalendarService.update(wrapper);
            return R.success("修改成功");
        }
        return R.error(500, "修改失败");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @param: search
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('calendar:list', 'calendar:query', 'user:calendar')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<OaCalendar> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaCalendar::getTitle, search);        //通过日程名称查询
        }

        IPage<OaCalendar> page = oaCalendarService.page(new Page<>(pageCount, pageSize), wrapper);
        return R.success("", page);
    }
}

