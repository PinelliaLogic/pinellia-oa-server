package com.pinellia.service.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.vo.OaUserVo;

public interface OaUserVoService extends IService<OaUserVo> {

    IPage<OaUserVo> pages(Integer pageNum, Integer pageSize, String search);

    OaUserVo getUserById(Long id);

}

