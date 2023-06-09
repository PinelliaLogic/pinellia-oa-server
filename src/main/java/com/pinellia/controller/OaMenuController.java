package com.pinellia.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaMenu;
import com.pinellia.entity.OaNotice;
import com.pinellia.service.OaMenuService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static cn.hutool.core.date.DateTime.now;

/**
 * (OaMenu)表控制层
 */
@RestController
@RequestMapping("/menu")
public class OaMenuController {

    @Autowired
    private OaMenuService oaMenuService;

    /**
     * 添加
     * @param oaMenu
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:add')")
    @PostMapping
    public R add(@RequestBody OaMenu oaMenu) {
        if (oaMenu == null) {
            return R.error(500, "输入字段不为空!");
        }
        //设置创建时间
        oaMenu.setCreateTime(now());
        oaMenuService.save(oaMenu);
        return R.success("成功添加");
    }

    /**
     * 删除
     * @param id
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:delete')")
    @DeleteMapping("/{id}")
    public R deleteById(@PathVariable Long id) {
        if (id == null) {
            return R.error(500, "删除失败");
        }
        oaMenuService.removeById(id);
        return R.success("删除成功");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     **/
    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:delete')")
    public R delete(@RequestBody Long[] ids) {
        boolean b = oaMenuService.removeByIds(Arrays.asList(ids));
        if (b) {
            return R.success("批量删除成功!");
        }
        return R.error(500, "批量删除失败!");
    }

    /**
     * 修改
     *
     * @param menu
     * @return
     **/
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:update')")
    @PutMapping
    public R updateById(@RequestBody OaMenu menu) {
        if (menu.getName() == null) {
            return R.error(500, "修改失败");
        }
        //设置更新时间
        UpdateWrapper<OaMenu> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", menu.getId()).set("update_time", now());
        oaMenuService.update(null, wrapper);
        oaMenuService.updateById(menu);
        return R.success("修改成功");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @param: search
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('menu:list', 'menu:query')")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "") String search
    ) {
        LambdaQueryWrapper<OaMenu> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(OaMenu::getName, search);        //通过名称查询
        }

        IPage<OaMenu> page = oaMenuService.page(new Page<>(pageCount, pageSize), wrapper);
        return R.success("", page);
    }

    /**
     * 查询所有菜单
     *
     * @param
     * @return com.pinellia.util.R
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAuthority('menu:part')")
    @GetMapping("/getAll")
    public R getAllRole() {
        //查询菜单并根据order_num排序
        List<OaMenu> menuList = oaMenuService.list(new QueryWrapper<OaMenu>().orderByAsc("order_num"));
        return R.success("", oaMenuService.menuTree(menuList));
    }

}
