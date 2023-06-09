package com.pinellia.service.impl.vo;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.vo.OaClockNotesVoDao;
import com.pinellia.entity.vo.OaClockNotesVo;
import com.pinellia.service.vo.OaClockNotesVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OaClockNotesVoServiceImpl extends ServiceImpl<OaClockNotesVoDao, OaClockNotesVo> implements OaClockNotesVoService {

    @Autowired
    private OaClockNotesVoDao oaClockNotesVoDao;

    @Override
    public List<OaClockNotesVo> getClock(Long id) {
        return oaClockNotesVoDao.getClock(id);
    }
}
