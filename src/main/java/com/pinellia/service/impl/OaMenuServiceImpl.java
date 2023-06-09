package com.pinellia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pinellia.dao.OaMenuDao;
import com.pinellia.entity.OaMenu;
import com.pinellia.service.OaMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * (OaMenu)表服务实现类
 *
 * @author pinellia
 * @since 2023-03-20 15:22:08
 */
@Service
public class OaMenuServiceImpl extends ServiceImpl<OaMenuDao, OaMenu> implements OaMenuService {

    @Autowired
    private OaMenuDao oaMenuDao;

    @Override
    public List<OaMenu> getMenu(Long id) {
        return oaMenuDao.getMenu(id);
    }

    @Override
    public List<OaMenu> menuTree(List<OaMenu> menuList) {
        ArrayList<OaMenu> resultMenu = new ArrayList<>();
        for (OaMenu menu : menuList) {

            //子菜单
            for (OaMenu oaMenu : menuList) {
                if (Objects.equals(oaMenu.getParentId(), menu.getId())) {
                    menu.getChildren().add(oaMenu);
                }
            }

            //主菜单
            if (menu.getParentId() == 0L) {
                resultMenu.add(menu);
            }
        }

        return resultMenu;
    }
}

