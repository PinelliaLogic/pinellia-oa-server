package com.pinellia.service.impl.vo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.vo.OaUserVoDao;
import com.pinellia.entity.vo.OaUserVo;
import com.pinellia.service.vo.OaUserVoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OaUserVoServiceImpl extends ServiceImpl<OaUserVoDao, OaUserVo> implements OaUserVoService {

    @Autowired
    private OaUserVoDao oaUserVoDao;

    @Override
    public IPage<OaUserVo> pages(Integer pageCount, Integer pageSize, String search) {
        Page<OaUserVo> userVoPage = new Page<>(pageCount, pageSize, false);
        QueryWrapper<OaUserVo> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(search)) {
            queryWrapper.like("username", search);
        }

        return oaUserVoDao.pages(userVoPage, queryWrapper);
    }

    @Override
    public OaUserVo getUserById(Long id) {
        return oaUserVoDao.getUserById(id);
    }
}

