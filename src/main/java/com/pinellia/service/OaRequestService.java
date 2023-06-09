package com.pinellia.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaRequest;
import org.apache.ibatis.annotations.Param;

/**
 * (OaRequest)表服务接口
 *
 * @author pinellia
 * @since 2023-04-24 20:18:05
 */
public interface OaRequestService extends IService<OaRequest> {

    public boolean add(Long userId, Long requestId);

    public String getNameByRequestId(Long id);

    IPage<OaRequest> selectMyPage(Long id, Page<OaRequest> page, Wrapper<OaRequest> queryWrapper);

}

