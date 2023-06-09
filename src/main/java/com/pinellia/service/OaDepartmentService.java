package com.pinellia.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.vo.OaDepartmentVo;

import java.util.List;


/**
 * (OaDepartment)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:06
 */
public interface OaDepartmentService extends IService<OaDepartment> {

    public void updateCount();

    IPage<OaUser> getUserByDepartmentIdPage(Long id, Page<OaUser> page, Wrapper<OaUser> queryWrapper);

    boolean deleteUserDepartmentByUserId(Long userId);

    OaDepartment getUserDepartment(Long userId);

    public boolean removeUser(Long id);

    public boolean divideUser(Long did, Long uid);

    public List<OaDepartmentVo> departmentTree(List<OaDepartment> departmentList);

}

