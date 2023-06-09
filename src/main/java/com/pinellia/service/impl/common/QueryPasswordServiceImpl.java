package com.pinellia.service.impl.common;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.common.QueryPasswordDao;
import com.pinellia.entity.common.QueryPassword;
import com.pinellia.service.common.QueryPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryPasswordServiceImpl extends ServiceImpl<QueryPasswordDao, QueryPassword> implements QueryPasswordService {

    @Autowired
    private QueryPasswordDao queryPasswordDao;

    @Override
    public String getPasswordById(Long id) {
        return queryPasswordDao.getPasswordById(id);
    }

    @Override
    public boolean updatePasswordById(String password, Long id) {
        return queryPasswordDao.updatePasswordById(password, id);
    }

    @Override
    public void updateStatusById(Long id) {
        queryPasswordDao.updateStatusById(id);
    }
}
