package com.pinellia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pinellia.entity.OaMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * (OaMenu)表服务接口
 *
 * @author pinellia
 * @since 2023-03-20 15:22:08
 */
public interface OaMenuService extends IService<OaMenu> {

    List<OaMenu> getMenu(Long id);

    List<OaMenu> menuTree(List<OaMenu> menu);
}

