package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaRecoverDao;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaRecover;
import com.pinellia.entity.vo.OaRecoverVo;
import com.pinellia.service.OaRecoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OaRecover)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:09
 */
@Service
public class OaRecoverServiceImpl extends ServiceImpl<OaRecoverDao, OaRecover> implements OaRecoverService {

    @Autowired
    private OaRecoverDao oaRecoverDao;

    //回收用户数据到回收站
    @Override
    public boolean recoverInfo(Long id) {
        return oaRecoverDao.recoverInfo(id);
    }

    //恢复数据
    @Override
    public boolean recover(Long id) {
        return oaRecoverDao.recover(id);
    }

    @Override
    public boolean updateDelete(Long id) {
        return oaRecoverDao.updateDelete(id);
    }

    @Override
    public OaDepartment getDepartmentByUserId(Long userId) {
        return oaRecoverDao.getDepartmentByUserId(userId);
    }

    @Override
    public List<OaJob> getJobByUserId(Long userId) {
        return oaRecoverDao.getJobByUserId(userId);
    }

}

