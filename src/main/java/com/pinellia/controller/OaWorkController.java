package com.pinellia.controller;



import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaRequest;
import com.pinellia.entity.OaWork;
import com.pinellia.service.OaDepartmentService;
import com.pinellia.service.OaWorkService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaWork)表控制层
 *
 * @author pinellia
 * @since 2023-04-25 13:20:24
 */
@RestController
@RequestMapping("/work")
public class OaWorkController {
    /**
     * 服务对象
     */
    @Autowired
    private OaWorkService oaWorkService;

    @Autowired
    private OaDepartmentService oaDepartmentService;

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:add')")
    @PostMapping("/{id}")
    public R add(@PathVariable Long id, @RequestBody OaWork oaWork) {
        if (id == null || oaWork == null) {
            return R.error(500, "创建失败!");
        }
        //设置创建时间
        oaWork.setCreateTime(now());
        //添加
        oaWorkService.save(oaWork);
        //插入到关联表,设置发布人
        oaWorkService.add(id, oaWork.getId());

        return R.success("创建成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:delete')")
    @Transactional
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败!");
        }
        oaWorkService.removeById(id);
        return R.success("删除成功!");
    }

    /**
     * 修改
     *
     * @param oaWork
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:update')")
    @Transactional
    @PutMapping
    public R update(@RequestBody OaWork oaWork) {
        if (oaWork == null) {
            return R.error(500, "修改失败!");
        }
        //设置修改时间
        oaWork.setUpdateTime(now());
        oaWorkService.updateById(oaWork);

        return R.success("修改成功");
    }

    /**
     * 用户分页
     *
     * @param pageCount
     * @return com.pinellia.util.R
     * @param: pageSize
     * @param: search
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('user:work', 'work:list')")
    @GetMapping("/{id}")
    public R userPage(@PathVariable Long id,
                      @RequestParam(defaultValue = "1") Integer pageCount,
                      @RequestParam(defaultValue = "15") Integer pageSize,
                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaWork> wrapper = Wrappers.lambdaQuery();
        //查询不等于0,即未发布的工作任务
        wrapper.ne(OaWork::getStatus, '0');

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaWork::getContent, search);        //通过内容查询
        }
        //自定义分页查询
        IPage<OaWork> pageResult = oaWorkService.selectMyPage(id,new Page<>(pageCount, pageSize), wrapper);
        List<OaWork> workList = pageResult.getRecords();
        for (OaWork oaWork : workList) {
            //判断是否超时
            if (oaWork.getEndTime().getTime() < now().getTime()) {
                UpdateWrapper<OaWork> workUpdateWrapper = new UpdateWrapper<>();
                workUpdateWrapper.eq("id", oaWork.getId()).set("status", '2');
            }
            //发布人的姓名
            String name = oaWorkService.getNameById(oaWork.getId());
            oaWork.setName(name);
            //执行人的姓名
            List<String> uname = oaWorkService.getUNameById(oaWork.getId());
            oaWork.setUname(uname);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("workList", workList);
        resultMap.put("total", pageResult.getTotal());

        return R.success("", resultMap);
    }

    /**
     * 分页
     *
     * @param pageCount
     * @return com.pinellia.util.R
     * @param: pageSize
     * @param: search
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:list', 'work:part')")
    @GetMapping
    public R page(@RequestParam(defaultValue = "1") Integer pageCount,
                  @RequestParam(defaultValue = "15") Integer pageSize,
                  @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaWork> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaWork::getContent, search);        //通过内容查询
        }
        Page<OaWork> pageResult = oaWorkService.page(new Page<>(pageCount, pageSize), wrapper);
        List<OaWork> workList = pageResult.getRecords();
        for (OaWork oaWork : workList) {
            //判断是否超时
            if (oaWork.getEndTime().getTime() < now().getTime()) {
                UpdateWrapper<OaWork> workUpdateWrapper = new UpdateWrapper<>();
                workUpdateWrapper.eq("id", oaWork.getId()).set("status", '2');
            }
            //发布人的姓名
            String name = oaWorkService.getNameById(oaWork.getId());
            oaWork.setName(name);
            //执行人的姓名
            List<String> uname = oaWorkService.getUNameById(oaWork.getId());
            oaWork.setUname(uname);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("workList", workList);
        resultMap.put("total", pageResult.getTotal());

        return R.success("", resultMap);
    }

    /**
     * 提交或撤销工作任务
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:list', 'user:work')")
    @PostMapping("/submit/{id}")
    public R submitRequest(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "操作失败!");
        }
        OaWork oaWork = oaWorkService.getById(id);

        UpdateWrapper<OaWork> wrapper = new UpdateWrapper<>();
        //0 未发布 1已发布
        if (oaWork.getStatus() == '0') {
            wrapper.eq("id", id).set("status", '1').set("begin_time", now());
            oaWorkService.update(null, wrapper);
            return R.success("提交成功");
        }
        if (oaWork.getStatus() == '1') {
            wrapper.eq("id", id).set("status", '4');
            oaWorkService.update(null, wrapper);
            return R.success("打回成功");
        }
        return R.error(500, "操作失败!");
    }

    /**
     * 获取该工作的已分配的员工
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:list', 'work:part')")
    @GetMapping("/tree/{id}")
    public R getUserByWorkId(@PathVariable Long id) {
        return R.success("", oaWorkService.getUserByWorkId(id));
    }

    /**
     * 获取部门员工信息
     * @param
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:list', 'work:part')")
    @GetMapping("/tree")
    public R getDepartmentUserTree() {
        return R.success("", oaDepartmentService.departmentTree(oaDepartmentService.list()));
    }

    /**
     * 获取待完成的工作
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getAll/{id}")
    public R getMyWork(@PathVariable Long id) {
        List<OaWork> work = oaWorkService.getMyWork(id);
        return R.success("", work);
    }

    /**
     * 查询工作数
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/num/{id}")
    public R getWorkNum(@PathVariable Long id) {
        ArrayList<Integer> workNum = new ArrayList<>();
        //未完成的工作数
        Integer incomplete = oaWorkService.getIncomplete(id);
        //超时未提交的工作数
        Integer timeout = oaWorkService.getTimeout(id);
        //已提交的工作数
        Integer submit = oaWorkService.getSubmit(id);
        //被打回的工作数
        Integer returnWork = oaWorkService.getReturn(id);
        workNum.add(incomplete);
        workNum.add(timeout);
        workNum.add(submit);
        workNum.add(returnWork);

        return R.success("", workNum);
    }

    /**
     * 分配工作
     * @param wid
     * @param: ids
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('work:list')")
    @PostMapping("/divide/{wid}")
    public R divideWork(@PathVariable Long wid, @RequestBody List<Long> ids) {
        if (wid == null) {
            return R.error(500, "分配失败!");
        }
        //删除该工作的执行人
        oaWorkService.removeId(wid);
        //分配
        for (Long id : ids) {
            oaWorkService.divideWork(id, wid);
        }
        return R.success("分配成功!");
    }

}

