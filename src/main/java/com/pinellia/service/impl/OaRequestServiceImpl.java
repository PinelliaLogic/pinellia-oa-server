package com.pinellia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaRequestDao;
import com.pinellia.entity.OaRequest;
import com.pinellia.service.OaRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (OaRequest)表服务实现类
 *
 * @author pinellia
 * @since 2023-04-24 20:18:05
 */
@Service
public class OaRequestServiceImpl extends ServiceImpl<OaRequestDao, OaRequest> implements OaRequestService {

    @Autowired
    private OaRequestDao oaRequestDao;

    @Override
    public boolean add(Long userId, Long requestId) {
        return oaRequestDao.add(userId, requestId);
    }

    @Override
    public String getNameByRequestId(Long id) {
        return oaRequestDao.getNameByRequestId(id);
    }

    @Override
    public IPage<OaRequest> selectMyPage(Long id, Page<OaRequest> page, Wrapper<OaRequest> queryWrapper) {
        return oaRequestDao.selectMyPage(id, page, queryWrapper);
    }
}

