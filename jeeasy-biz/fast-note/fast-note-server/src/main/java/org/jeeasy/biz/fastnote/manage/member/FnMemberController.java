package org.jeeasy.biz.fastnote.manage.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.biz.fastnote.modules.member.service.FnMemberService;
import org.jeeasy.common.core.annotation.dict.DictTranslation;
import org.jeeasy.common.core.base.SimpleBaseController;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.*;


/**
 * FastNote用户控制器
 *
 * @author AlpsDDJ
 * @description FastNote用户
 * @date 2022/9/11 13:52:05
 */
@Slf4j
@RestController("FnMemberController")
@RequiredArgsConstructor
@Tag(name = "FastNote用户")
@RequestMapping("/fn/member")
public class FnMemberController extends SimpleBaseController<FnMemberService, FnMember> {

    @GetMapping
    @DictTranslation
    @Operation(summary = "FastNote用户列表", description = "FastNote用户列表")
    public R<IPage<FnMember>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        return super.queryPage(queryPageModel, req);
    }

    /**
     * @param id
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:05
     */
    @GetMapping("/{id}")
    @DictTranslation
    @Operation(summary = "根据ID查找FastNote用户", description = "根据ID查找FastNote用户")
    public R<FnMember> info(@PathVariable("id") String id) {
        return super.getById(id);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:03
     */
    @PutMapping
    @Operation(summary = "编辑FastNote用户", description = "编辑FastNote用户")
    public R<?> edit(@RequestBody FnMember entity) {
        return super.update(entity);
    }

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping
    @Operation(summary = "添加FastNote用户", description = "添加FastNote用户")
    public R<?> add(@RequestBody FnMember entity) {
        return super.insert(entity);
    }

    /**
     * @param id
     * @return {@link R<?>}
     * @author AlpsDDJ
     * @date 2020/11/21 22:11
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除FastNote用户", description = "根据ID删除FastNote用户")
    public R<?> remove(@PathVariable("id") String id) {
        return super.deleteById(id);
    }

    /**
     * @param ids
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:10
     */
    @DeleteMapping("/batch")
    @Operation(summary = "批量删除FastNote用户", description = "批量删除FastNote用户")
    public R<?> batchDelete(@RequestParam(name = "ids") String ids) {
        return super.batchDelete(ids);
    }
}