package org.jeeasy.biz.fastnote.api.note;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.jeeasy.biz.fastnote.modules.note.domain.FnNote;
import org.jeeasy.biz.fastnote.modules.note.service.FnNoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.annotation.controller.ApiController;
import org.jeeasy.common.core.base.SimpleBaseApi;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * FastNote用户控制器
 *
 * @author AlpsDDJ
 * @description FastNote用户
 * @date 2022/9/11 13:52:05
 */
@Slf4j
@RequiredArgsConstructor
@Tag(name = "FastNote笔记")
@ApiController("/fn/note")
public class FnNoteApi extends SimpleBaseApi<FnNoteService, FnNote> {

    /**
     * @param entity
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @PostMapping("save")
    @Operation(summary = "保存笔记", description = "保存笔记")
    public R<?> register(@RequestBody FnNote entity) {
        entity.setFtMemberId(StpUtil.getLoginId().toString());
        super.service.save(entity);
        return R.ok("保存成功");
    }

    /**
     * @return {@link R}
     * @author mobie
     * @date 2020/11/21 16:18
     */
    @GetMapping("list")
    @Operation(summary = "笔记列表", description = "笔记列表")
    public R<IPage<FnNote>> list(QueryPageModel queryPageModel, HttpServletRequest req) {
        QueryWrapper<FnNote> wapper = getWapper(req);
        wapper.lambda().eq(FnNote::getFtMemberId, StpUtil.getLoginId());
        return super.queryPage(queryPageModel, wapper);
    }

}