package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaJobDao;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OaJob)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:08
 */
@Service
public class OaJobServiceImpl extends ServiceImpl<OaJobDao, OaJob> implements OaJobService {

    @Autowired
    private OaJobDao oaJobDao;

    @Override
    public void updateCount() {
        oaJobDao.updateCount();
    }

    @Override
    public List<OaUser> getUserByJobId(Long id) {
        return oaJobDao.getUserByJobId(id);
    }

    @Override
    public boolean deleteUserJobByUserId(Long userId) {
        return oaJobDao.deleteUserJobByUserId(userId);
    }

    @Override
    public List<OaJob> getUserJob(Long userId) {
        return oaJobDao.getUserJob(userId);
    }

    @Override
    public IPage<OaUser> getUserByJobPage(Long id, Page<OaUser> page, Wrapper<OaUser> queryWrapper) {
        return oaJobDao.getUserByJobPage(id, page, queryWrapper);
    }

    @Override
    public boolean removeUser(Long id) {
        return oaJobDao.removeUser(id);
    }

    @Override
    public boolean divideUser(Long jid, Long uid) {
        return oaJobDao.divideUser(jid, uid);
    }
}

