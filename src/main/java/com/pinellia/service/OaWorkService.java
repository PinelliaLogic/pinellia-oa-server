package com.pinellia.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaUser;
import com.pinellia.entity.OaWork;

import java.util.List;

/**
 * (OaWork)表服务接口
 *
 * @author pinellia
 * @since 2023-04-25 13:20:24
 */
public interface OaWorkService extends IService<OaWork> {

    public boolean add(Long uid, Long wid);

    IPage<OaWork> selectMyPage(Long id, Page<OaWork> page, Wrapper<OaWork> queryWrapper);

    public String getNameById(Long id);

    public List<String> getUNameById(Long id);

    public List<OaUser> getUserByWorkId(Long id);

    public List<OaWork> getMyWork(Long id);

    public Integer getIncomplete(Long id);

    public Integer getTimeout(Long id);

    public Integer getSubmit(Long id);

    public Integer getReturn(Long id);

    public boolean removeId(Long id);

    public boolean divideWork(Long uid, Long wid);

}

