package com.pinellia.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaResources;
import com.pinellia.entity.OaUser;

import java.util.List;

/**
 * (OaResources)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:09
 */
public interface OaResourcesService extends IService<OaResources> {

    public boolean addResources(Long uid, Long id);

    public boolean deleteResourcesById(Long id);

    IPage<OaResources> selectMyPage(Long id, Page<OaResources> page, Wrapper<OaResources> queryWrapper);

    List<OaResources> getAll(Long id);

    public List<OaUser> getDivideUser(Long id);

    public boolean removeResourcesById(Long id);

    public boolean divideResources(Long uid, Long rid);

}

