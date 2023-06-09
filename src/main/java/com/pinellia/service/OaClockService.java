package com.pinellia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaClock;

/**
 * (OaClock)表服务接口
 *
 * @author pinellia
 * @since 2023-04-08 15:22:46
 */
public interface OaClockService extends IService<OaClock> {

    public OaClock getClock();

    public void updateCount();

    public void deleteClock(Long clockId);

    public void deleteClockNotes(Long clockId);

}

