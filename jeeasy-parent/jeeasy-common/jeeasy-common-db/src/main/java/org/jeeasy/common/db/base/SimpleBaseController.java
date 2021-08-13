package org.jeeasy.common.db.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.db.model.QueryPageModel;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.db.tools.QueryGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alps
 */
@Slf4j
public class SimpleBaseController<S extends IService<T>, T> {

    @Autowired
    protected S service;

    /**
     * 列表分页查询
     *
     * @param entity
     * @param page
     * @param req
     * @return {@link R< IPage<T>>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    protected R<IPage<T>> page(T entity, IPage<T> page, HttpServletRequest req) {
        QueryWrapper<T> queryWrapper = QueryGenerator.createWrapper(entity, req.getParameterMap());
        return R.ok(service.page(page, queryWrapper));
    }

    /**
     * 列表分页查询
     *
     * @param entity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return {@link R< IPage<T>>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    protected R<IPage<T>> query(T entity, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        QueryWrapper<T> queryWrapper = QueryGenerator.createWrapper(entity, req.getParameterMap());
        return R.ok(service.page(new Page<T>(pageNo, pageSize), queryWrapper));
    }

    /**
     * 列表分页查询
     *
     * @param query
     * @param clazz
     * @param req
     * @return {@link R< IPage<T>>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    protected R<IPage<T>> query(QueryPageModel query, HttpServletRequest req, Class<T> clazz) {
        QueryWrapper<T> queryWrapper = QueryGenerator.createWrapper(clazz, req.getParameterMap());
        return R.ok(service.page(new Page<T>(query.getCurrent(), query.getSize()), queryWrapper));
    }

    /**
     * 通过id获取对象
     *
     * @param id
     * @return {@link R<T>}
     * @author mobie
     * @date 2020/11/21 16:22
     */
    protected R<T> getById(Serializable id) {
        return R.ok(service.getById(id));
    }

    /**
     * 保存对象
     *
     * @param entity
     * @return {@link R<?>}
     * @author mobie
     * @date 2020/11/21 16:23
     */
    protected R<?> insert(T entity) {
        if (service.save(entity)) {
            return R.ok("新增成功");
        } else {
            return R.error("新增失败");
        }
    }

    /**
     * 修改对象
     *
     * @param entity
     * @return {@link R<?>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    protected R<?> update(T entity) {
        if (service.updateById(entity)) {
            return R.ok("编辑成功");
        } else {
            return R.error("编辑失败");
        }
    }

    /**
     * 根据id删除对象
     *
     * @param id
     * @return {@link R<?>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    protected R<?> deleteById(Serializable id) {
        if (service.removeById(id)) {
            return R.ok("删除成功");
        } else {
            return R.error("删除失败");
        }
    }

    /**
     * 根据ids批量删除对象
     *
     * @param ids
     * @return {@link R<?>}
     * @author mobie
     * @date 2020/11/21 16:24
     */
    public R<?> deleteBatch(String ids) {
        if (Tools.isEmpty(ids)) {
            return R.error("未选中数据");
        } else {
            List<String> ls = Arrays.asList(ids.split(","));
            service.removeByIds(ls);
            return R.ok("未选中数据");
        }
    }

}