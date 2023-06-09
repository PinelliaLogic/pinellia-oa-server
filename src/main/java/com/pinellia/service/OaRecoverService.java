package com.pinellia.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaDepartment;
import com.pinellia.entity.OaJob;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaRecover;
import com.pinellia.entity.vo.OaRecoverVo;
import com.pinellia.entity.vo.OaUserVo;

import java.util.List;

/**
 * (OaRecover)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:09
 */
public interface OaRecoverService extends IService<OaRecover> {

    boolean recoverInfo(Long id);

    boolean recover(Long id);

    boolean updateDelete(Long id);

    public OaDepartment getDepartmentByUserId(Long userId);

    public List<OaJob> getJobByUserId(Long userId);

}

