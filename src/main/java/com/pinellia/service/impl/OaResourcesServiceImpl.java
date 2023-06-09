package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaResourcesDao;
import com.pinellia.entity.OaResources;
import com.pinellia.entity.OaUser;
import com.pinellia.service.OaResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (OaResources)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:09
 */
@Service
public class OaResourcesServiceImpl extends ServiceImpl<OaResourcesDao, OaResources> implements OaResourcesService {

    @Autowired
    private OaResourcesDao oaResourcesDao;

    @Override
    public boolean addResources(Long uid, Long id) {
        return oaResourcesDao.addResources(uid, id);
    }

    @Override
    public boolean deleteResourcesById(Long id) {
        return oaResourcesDao.deleteResourcesById(id);
    }

    @Override
    public IPage<OaResources> selectMyPage(Long id, Page<OaResources> page, Wrapper<OaResources> queryWrapper) {
        return oaResourcesDao.selectMyPage(id, page, queryWrapper);
    }

    @Override
    public List<OaResources> getAll(Long id) {
        return oaResourcesDao.getAll(id);
    }

    @Override
    public List<OaUser> getDivideUser(Long id) {
        return oaResourcesDao.getDivideUser(id);
    }

    @Override
    public boolean removeResourcesById(Long id) {
        return oaResourcesDao.removeResourcesById(id);
    }

    @Override
    public boolean divideResources(Long uid, Long rid) {
        return oaResourcesDao.divideResources(uid, rid);
    }
}

