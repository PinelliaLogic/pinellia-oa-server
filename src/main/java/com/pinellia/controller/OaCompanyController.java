package com.pinellia.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pinellia.entity.OaCompany;
import com.pinellia.service.OaCompanyService;
import com.pinellia.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * (OaCompany)表控制层
 *
 * @author pinellia
 * @since 2023-04-11 17:06:56
 */
@RestController
@RequestMapping("/company")
public class OaCompanyController {
    /**
     * 服务对象
     */
    @Autowired
    private OaCompanyService oaCompanyService;

    /**
     * 添加
     *
     * @param oaCompany
     * @return com.pinellia.util.R
     */
    @PostMapping
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('company:add')")
    public R add(@RequestBody OaCompany oaCompany) {
        boolean save = oaCompanyService.save(oaCompany);
        if (save) {
            return R.success("创建成功!");
        }
        return R.error(500, "创建失败！");
    }

    /**
     * 删除
     *
     * @param id
     * @return com.pinellia.util.R
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('company:delete')")
    public R delete(@PathVariable Long id) {
        boolean b = oaCompanyService.removeById(id);
        if (b) {
            return R.success("删除成功!");
        }
        return R.error(500, "删除失败!");
    }

    /**
     * 修改
     *
     * @param oaCompany
     * @return com.pinellia.util.R
     */
    @PutMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') OR hasAnyAuthority('company:update')")
    public R update(@RequestBody OaCompany oaCompany) {
        boolean b = oaCompanyService.updateById(oaCompany);
        if (b) {
            return R.success("修改成功!");
        }
        return R.error(500, "修改成功!");
    }

    /**
     * 分页
     * @param pageCount
     * @param: pageSize
     * @return com.pinellia.util.R
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public R page(
            @RequestParam(defaultValue = "1") Integer pageCount,
            @RequestParam(defaultValue = "15") Integer pageSize) {

        IPage<OaCompany> page = oaCompanyService.page(new Page<>(pageCount, pageSize), null);
        return R.success("", page);
    }
}

