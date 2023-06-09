package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaRequest;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaRequestService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaRequest)表控制层
 *
 * @author pinellia
 * @since 2023-04-24 20:18:05
 */
@RestController
@RequestMapping("/request")
public class OaRequestController {
    /**
     * 服务对象
     */
    @Autowired
    private OaRequestService oaRequestService;

    /**
     * 添加
     *
     * @param id
     * @return com.pinellia.util.R
     * @param: oaRequest
     */
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('request:add', 'user:request')")
    @PostMapping("/{id}")
    public R add(@PathVariable Long id, @RequestBody OaRequest oaRequest) {
        if (id == null || oaRequest == null) {
            return R.error(500, "创建失败!");
        }
        //设置创建时间
        oaRequest.setCreateTime(now());
        //添加
        oaRequestService.save(oaRequest);
        //插入到关联表
        oaRequestService.add(id, oaRequest.getId());

        return R.success("创建成功");
    }

    /**
     * 删除
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('request:delete', 'user:request')")
    @Transactional
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败!");
        }
        oaRequestService.removeById(id);
        return R.success("删除成功!");
    }

    /**
     * 修改
     *
     * @param oaRequest
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('request:update', 'user:request')")
    @Transactional
    @PutMapping
    public R update(@RequestBody OaRequest oaRequest) {
        if (oaRequest == null) {
            return R.error(500, "修改失败!");
        }
        //设置修改时间
        oaRequest.setUpdateTime(now());
        oaRequestService.updateById(oaRequest);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('user:request')")
    @GetMapping("/{id}")
    public R userPage(@PathVariable Long id,
                      @RequestParam(defaultValue = "1") Integer pageCount,
                      @RequestParam(defaultValue = "15") Integer pageSize,
                      @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaRequest> wrapper = Wrappers.lambdaQuery();

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaRequest::getContent, search);        //通过内容查询
        }
        IPage<OaRequest> pageResult = oaRequestService.selectMyPage(id,new Page<>(pageCount, pageSize), wrapper);
        List<OaRequest> requestList = pageResult.getRecords();
        for (OaRequest oaRequest : requestList) {
            String name = oaRequestService.getNameByRequestId(oaRequest.getId());
            oaRequest.setName(name);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("requestList", requestList);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('request:list')")
    @GetMapping
    public R page(@RequestParam(defaultValue = "1") Integer pageCount,
                  @RequestParam(defaultValue = "15") Integer pageSize,
                  @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OaRequest> wrapper = Wrappers.lambdaQuery();
        wrapper.gt(OaRequest::getStatus, 0);        //获取已提交,已撤回，已批准，已拒绝的申请
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaRequest::getTime, search);        //通过申请时间查询
        }
        Page<OaRequest> pageResult = oaRequestService.page(new Page<>(pageCount, pageSize), wrapper);
        List<OaRequest> requestList = pageResult.getRecords();
        for (OaRequest oaRequest : requestList) {
            String name = oaRequestService.getNameByRequestId(oaRequest.getId());
            oaRequest.setName(name);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("requestList", requestList);
        resultMap.put("total", pageResult.getTotal());

        return R.success("", resultMap);
    }

    /**
     * 提交或撤销申请
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('request:list', 'user:request')")
    @PostMapping("/submit/{id}")
    public R submitRequest(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "操作失败!");
        }
        OaRequest request = oaRequestService.getById(id);

        UpdateWrapper<OaRequest> wrapper = new UpdateWrapper<>();
        //0 未申请 1已撤回
        if (request.getStatus() <= '1') {
            wrapper.eq("id", id).set("status", '2').set("time", now());
            oaRequestService.update(null, wrapper);
            return R.success("提交成功");
        }
        if (request.getStatus() == '2') {
            wrapper.eq("id", id).set("status", '1');
            oaRequestService.update(null, wrapper);
            return R.success("撤销成功");
        }
        return R.error(500, "操作失败!");
    }

    /**
     * 批准申请
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('request:list')")
    @PostMapping("/allow/{id}")
    public R allowRequest(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "失败");
        }
        UpdateWrapper<OaRequest> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("status", 3);
        oaRequestService.update(null, wrapper);

        return R.success("已批准");
    }

    /**
     * 拒绝申请
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('request:list')")
    @PostMapping("/refuse/{id}")
    public R refuseRequest(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "失败");
        }
        UpdateWrapper<OaRequest> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id).set("status", 4);
        oaRequestService.update(null, wrapper);

        return R.success("已拒绝");
    }

}

