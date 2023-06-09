package com.pinellia.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.pinellia.entity.OaClock;
import com.pinellia.entity.OaClockNotes;
import com.pinellia.service.OaClockNotesService;
import com.pinellia.service.OaClockService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaClockNotes)表控制层
 *
 * @author pinellia
 * @since 2023-04-08 15:22:46
 */
@RestController
@RequestMapping("/notes")
public class OaClockNotesController {
    /**
     * 服务对象
     */
    @Autowired
    private OaClockNotesService oaClockNotesService;

    @Autowired
    private OaClockService oaClockService;

    /**
     * 用户签到
     * @param id
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}")
    public R punchClock(@PathVariable Long id) {
        if (id == null || id == 0L) {
            return R.error(500, "打卡失败!");
        }
        //获取进行中的打卡
        OaClock clock = oaClockService.getClock();

        //插入数据到oa_clock_notes,未签到is_punch为0
        boolean b = oaClockNotesService.setClock(id);

        if (b) {
            //获取oa_clock_notes用户签到记录信息
            OaClockNotes userClockNotes = oaClockNotesService.getUserById(id);

            //插入数据到oa_cc
            oaClockNotesService.setCreateNotes(clock.getId(), userClockNotes.getId());

            //修改字段为1，已签到
            UpdateWrapper<OaClockNotes> wrapper = new UpdateWrapper<>();
            wrapper.eq("id", userClockNotes.getId()).set("is_punch", "1");
            oaClockNotesService.update(null, wrapper);

            return R.success("打卡成功");
        }
        return R.error(500, "打卡失败!");
    }

    /**
     * 获取正在进行中的打卡
     * @param
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public R getClock() {
        OaClock clock = oaClockService.getClock();
        return R.success("", clock);
    }

    /**
     * 获取用户的签到信息
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/{clockId}")
    public R getUserClockInfo(@PathVariable Long id, @PathVariable Long clockId) {
        OaClockNotes userClock = oaClockNotesService.getUserClockInfo(clockId, id);
        return R.success("", userClock);
    }
}

