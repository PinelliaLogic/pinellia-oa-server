package com.pinellia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaClockNotes;

/**
 * (OaClockNotes)表服务接口
 *
 * @author pinellia
 * @since 2023-04-08 15:22:47
 */
public interface OaClockNotesService extends IService<OaClockNotes> {

    public boolean setClock(Long id);

    public boolean setCreateNotes(Long clockId, Long notesId);

    public OaClockNotes getUserClockInfo(Long clockId, Long id);

    public OaClockNotes getUserById(Long id);

}

