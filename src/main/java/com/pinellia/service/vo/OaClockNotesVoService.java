package com.pinellia.service.vo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.vo.OaClockNotesVo;

import java.util.List;

public interface OaClockNotesVoService extends IService<OaClockNotesVo> {

    List<OaClockNotesVo> getClock(Long id);

}
