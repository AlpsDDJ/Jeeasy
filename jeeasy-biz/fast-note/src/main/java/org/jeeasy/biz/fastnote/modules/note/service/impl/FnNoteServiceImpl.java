package org.jeeasy.biz.fastnote.modules.note.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.biz.fastnote.modules.note.domain.FnNote;
import org.jeeasy.biz.fastnote.modules.note.mapper.FnNoteMapper;
import org.jeeasy.biz.fastnote.modules.note.service.FnNoteService;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2022/9/11
 */
@Service
public class FnNoteServiceImpl extends ServiceImpl<FnNoteMapper, FnNote> implements FnNoteService {
}
