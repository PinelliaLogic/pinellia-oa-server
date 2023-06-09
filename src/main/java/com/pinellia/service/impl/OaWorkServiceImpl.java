package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaWorkDao;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.OaWork;
import com.pinellia.service.OaWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OaWork)表服务实现类
 *
 * @author pinellia
 * @since 2023-04-25 13:20:24
 */
@Service
public class OaWorkServiceImpl extends ServiceImpl<OaWorkDao, OaWork> implements OaWorkService {

    @Autowired
    private OaWorkDao oaWorkDao;

    @Override
    public boolean add(Long uid, Long wid) {
        return oaWorkDao.add(uid, wid);
    }

    @Override
    public IPage<OaWork> selectMyPage(Long id, Page<OaWork> page, Wrapper<OaWork> queryWrapper) {
        return oaWorkDao.selectMyPage(id, page, queryWrapper);
    }

    @Override
    public String getNameById(Long uid) {
        return oaWorkDao.getNameById(uid);
    }

    @Override
    public List<String> getUNameById(Long id) {
        return oaWorkDao.getUNameById(id);
    }

    @Override
    public List<OaUser> getUserByWorkId(Long id) {
        return oaWorkDao.getUserByWorkId(id);
    }

    @Override
    public List<OaWork> getMyWork(Long id) {
        return oaWorkDao.getMyWork(id);
    }

    @Override
    public Integer getIncomplete(Long id) {
        return oaWorkDao.getIncomplete(id);
    }

    @Override
    public Integer getTimeout(Long id) {
        return oaWorkDao.getTimeout(id);
    }

    @Override
    public Integer getSubmit(Long id) {
        return oaWorkDao.getSubmit(id);
    }

    @Override
    public Integer getReturn(Long id) {
        return oaWorkDao.getReturn(id);
    }

    @Override
    public boolean removeId(Long id) {
        return oaWorkDao.removeId(id);
    }

    @Override
    public boolean divideWork(Long uid, Long wid) {
        return oaWorkDao.divideWork(uid, wid);
    }
}

