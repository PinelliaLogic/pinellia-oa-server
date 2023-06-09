package com.pinellia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaClockNotesDao;
import com.pinellia.entity.OaClockNotes;
import com.pinellia.service.OaClockNotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (OaClockNotes)表服务实现类
 *
 * @author pinellia
 * @since 2023-04-08 15:22:47
 */
@Service
public class OaClockNotesServiceImpl extends ServiceImpl<OaClockNotesDao, OaClockNotes> implements OaClockNotesService {

    @Autowired
    private OaClockNotesDao oaClockNotesDao;

    @Override
    public boolean setClock(Long id) {
        return oaClockNotesDao.setClock(id);
    }

    @Override
    public boolean setCreateNotes(Long clockId, Long notesId) {
        return oaClockNotesDao.setCreateNotes(clockId, notesId);
    }

    @Override
    public OaClockNotes getUserClockInfo(Long clockId, Long id) {
        return oaClockNotesDao.getUserClockInfo(clockId, id);
    }

    @Override
    public OaClockNotes getUserById(Long id) {
        return oaClockNotesDao.getUserById(id);
    }
}

