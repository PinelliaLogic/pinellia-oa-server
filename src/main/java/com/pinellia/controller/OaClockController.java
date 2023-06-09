package com.pinellia.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaClock;
import com.pinellia.entity.vo.OaClockNotesVo;
import com.pinellia.service.OaClockService;
import com.pinellia.service.vo.OaClockNotesVoService;
import com.pinellia.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaClock)表控制层
 */
@Slf4j
@RestController
@RequestMapping("/clock")
public class OaClockController {
    /**
     * 服务对象
     */
    @Autowired
    private OaClockService oaClockService;

    @Autowired
    private OaClockNotesVoService oaClockNotesVoService;

    /**
     * 创建考勤
     * @param oaClock
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:add')")
    @PostMapping
    public R insert(@RequestBody OaClock oaClock) {
        if (oaClock != null) {
            //判断时间当前时间是否在beginTime和endTime之间
            if (oaClock.getBeginTime().getTime() < now().getTime() && oaClock.getEndTime().getTime() > now().getTime()) {
                //处于之间表示签到已开始,状态设为1
                oaClock.setStatus('1');
            }
            //设置创建时间
            oaClock.setCreateTime(now());
            oaClockService.save(oaClock);
            return R.success("创建成功!");
        }
        return R.error(500, "创建失败");
    }

    /**
     * 修改考勤
     * @param oaClock
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:update')")
    @PutMapping
    public R update(@RequestBody OaClock oaClock) {
        boolean b = oaClockService.updateById(oaClock);
        if (b) {
            return R.success("修改成功!");
        }
        return R.error(500, "修改失败");
    }

    /**
     * 删除数据
     * @param id
     * @return com.pinellia.util.R
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:delete')")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        //删除oa_clock中的数据
        boolean b = oaClockService.removeById(id);
        if (b) {
            //先删除oa_clock_notes表中的对应数据
            oaClockService.deleteClockNotes(id);
            //再删除oa_cc表中的数据
            oaClockService.deleteClock(id);
            return R.success("删除成功");
        }
        return R.error(500, "删除失败");
    }

    /**
     * 批量删除
     * @param ids
     * @return
     **/
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:delete')")
    public R delete(@RequestBody Long[] ids) {
        boolean b = oaClockService.removeByIds(Arrays.asList(ids));
        if (b) {
            return R.success("批量删除成功!");
        }
        return R.error(500, "批量删除失败!");
    }

    /**
     * 分页
     * @param pageNum
     * @param: pageSize
     * @param: beginTime
     * @param: endTime
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:list')")
    @GetMapping
    public R findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "20") Integer pageSize,
                      @RequestParam(defaultValue = "") String search) throws ParseException {
        //更新签到人数
        oaClockService.updateCount();
        List<OaClock> list = oaClockService.list();
        for (OaClock oaClock : list) {
            if (oaClock.getEndTime().getTime() < now().getTime()) {
                UpdateWrapper<OaClock> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", oaClock.getId()).set("status", '2');
                oaClockService.update(null, updateWrapper);
            }
        }

        LambdaQueryWrapper<OaClock> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotEmpty(search)) {
            wrapper.like(OaClock::getTitle, search);      //通过标题查询
        }
        IPage<OaClock> clockPage = oaClockService.page(new Page<>(pageNum, pageSize), wrapper);
        return R.success("", clockPage);
    }

    /**
     * 考勤记录
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:list')")
    @GetMapping("/{id}")
    public R clockInfoPage(@PathVariable Long id) {
        List<OaClockNotesVo> clock = oaClockNotesVoService.getClock(id);
        log.error("" + clock);
        return R.success("", clock);
    }

    /**
     * 开始签到
     * @param id
     * @param: value
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:add')")
    @PostMapping("/start/{id}")
    public R start(@PathVariable Long id, @RequestBody Long value) throws Exception {
        OaClock clock = oaClockService.getById(id);
        //签到人数不等于0，表示重新开始签到
        if (clock.getCount() != 0) {
            //先删除oa_clock_notes表中的对应数据
            oaClockService.deleteClockNotes(id);
            //再删除oa_cc表中的数据
            oaClockService.deleteClock(id);
        }
        UpdateWrapper<OaClock> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("begin_time", now())
                                    .set("end_time", new Date(now().getTime() + (value * 60 * 1000)))
                                    .set("status", '1');
//        System.out.println("时间" + new Date(now().getTime() + (value * 60 * 1000)));
        oaClockService.update(null, wrapper);
        return R.success("");
    }

    /**
     * 结束签到
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('clock:add')")
    @PostMapping("/end/{id}")
    public R end(@PathVariable Long id) {
        UpdateWrapper<OaClock> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("end_time", DateTime.now()).set("status", '0');
        oaClockService.update(null, wrapper);
        return R.success("");
    }

}

