package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaDepartmentDao;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.vo.OaDepartmentVo;
import com.pinellia.service.OaDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * (OaDepartment)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:07
 */
@Service
public class OaDepartmentServiceImpl extends ServiceImpl<OaDepartmentDao, OaDepartment> implements OaDepartmentService {

    @Autowired
    private OaDepartmentDao oaDepartmentDao;

    @Override
    public void updateCount() {
        oaDepartmentDao.updateCount();
    }

    @Override
    public IPage<OaUser> getUserByDepartmentIdPage(Long id, Page<OaUser> page, Wrapper<OaUser> queryWrapper) {
        return oaDepartmentDao.getUserByDepartmentIdPage(id, page, queryWrapper);
    }

    @Override
    public boolean deleteUserDepartmentByUserId(Long userId) {
        return oaDepartmentDao.deleteUserDepartmentByUserId(userId);
    }

    @Override
    public OaDepartment getUserDepartment(Long userId) {
        return oaDepartmentDao.getUserDepartment(userId);
    }

    @Override
    public boolean removeUser(Long id) {
        return oaDepartmentDao.removeUser(id);
    }

    @Override
    public boolean divideUser(Long did, Long uid) {
        return oaDepartmentDao.divideUser(did, uid);
    }

    //部门用户树
    @Override
    public List<OaDepartmentVo> departmentTree(List<OaDepartment> departmentList) {
        ArrayList<OaDepartmentVo> resultDepartment = new ArrayList<>();
        for (OaDepartment department : departmentList) {
            List<OaUser> user = oaDepartmentDao.getUserByDepartmentId(department.getId());

            OaDepartmentVo od = new OaDepartmentVo(department.getId(), department.getDepartmentName(), user);

            resultDepartment.add(od);
        }
        return resultDepartment;
    }

}

