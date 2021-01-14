package org.jeeasy.common.db.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
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
    protected S baseService;

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
        return R.ok(baseService.page(page, queryWrapper));
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
        return R.ok(baseService.page(new Page<T>(pageNo, pageSize), queryWrapper));
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
        return R.ok(baseService.getById(id));
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
        if (baseService.save(entity)) {
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
        if (baseService.updateById(entity)) {
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
        if (baseService.removeById(id)) {
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
        R<?> result = new R<>();
        if (Tools.isEmpty(ids)) {
            result.faild("未选中数据");
        } else {
            List<String> ls = Arrays.asList(ids.split(","));
            baseService.removeByIds(ls);
            result.success("批量删除成功");
        }
        return result;
    }

}