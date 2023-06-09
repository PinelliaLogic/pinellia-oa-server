package com.pinellia.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaUser;

import java.util.List;

/**
 * (OaJob)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:07
 */
public interface OaJobService extends IService<OaJob> {

    void updateCount();

    List<OaUser> getUserByJobId(Long id);

    boolean deleteUserJobByUserId(Long userId);

    public List<OaJob> getUserJob(Long userId);

    IPage<OaUser> getUserByJobPage(Long id, Page<OaUser> page, Wrapper<OaUser> queryWrapper);

    public boolean removeUser(Long id);

    public boolean divideUser(Long jid, Long uid);

}

