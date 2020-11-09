package org.jeeasy.common.db.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.tools.QueryGenerator;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class SimpleBaseController<S extends IService<T>, T> {
    @Resource
    protected S service;

    protected R<Page<T>> query(T entity, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<T> queryWrapper = QueryGenerator.createWrapper(entity, req.getParameterMap());
        Page<T> page = new Page<T>(pageNo, pageSize);
        return R.ok(service.page(page, queryWrapper));
    }

    protected R<T> getById(Serializable id) {
        return R.ok(service.getById(id));
    }

    protected R<?> insert(T entity) {
        if (service.save(entity)) {
            return R.ok("新增成功.");
        } else {
            return R.error("新增失败.");
        }
    }

    protected R<?> update(T entity) {
        if (service.updateById(entity)) {
            return R.ok("编辑成功.");
        } else {
            return R.error("编辑失败.");
        }
    }

    protected R<?> deleteById(Serializable id) {
        if (service.removeById(id)) {
            return R.ok("删除成功.");
        } else {
            return R.error("删除失败.");
        }
    }

    public R<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        R<?> result = new R<>();
        if (Tools.isEmpty(ids)) {
            result.faild("未选中租户.");
        } else {
            List<String> ls = Arrays.asList(ids.split(","));
            service.removeByIds(ls);
            result.success("批量删除成功.");
        }
        return result;
    }

}