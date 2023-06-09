package com.pinellia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaClockDao;
import com.pinellia.entity.OaClock;
import com.pinellia.service.OaClockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (OaClock)表服务实现类
 *
 * @author pinellia
 * @since 2023-04-08 15:22:46
 */
@Service
public class OaClockServiceImpl extends ServiceImpl<OaClockDao, OaClock> implements OaClockService {

    @Autowired
    private OaClockDao oaClockDao;

    @Override
    public OaClock getClock() {
        return oaClockDao.getClock();
    }

    @Override
    public void updateCount() {
        oaClockDao.updateCount();
    }

    @Override
    public void deleteClock(Long clockId) {
        oaClockDao.deleteClock(clockId);
    }

    @Override
    public void deleteClockNotes(Long clockId) {
        oaClockDao.deleteClockNotes(clockId);
    }
}

