package org.jeeasy.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.domain.GenTableField;
import org.jeeasy.generate.mapper.GenTableMapper;
import org.jeeasy.generate.service.GenTableFieldService;
import org.jeeasy.generate.service.GenTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class GenTableServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GenTableService {
    @Resource
    GenTableFieldService tableFieldService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWithFields(GenTable entity) {
        List<GenTableField> tableFields = entity.getTableFields();
        String tableId = entity.getId();
        if (Tools.isNotEmpty(tableFields)) {
            Set<String> ids = new HashSet<>();
            tableFields.forEach(field -> {
                field.setTableId(tableId);
                String fieldId = field.getId();
                if (Tools.isNotEmpty(fieldId)) {
                    ids.add(fieldId);
                }
            });
            if (Tools.isNotEmpty(ids)) {
                tableFieldService.remove(new QueryWrapper<GenTableField>().lambda().eq(GenTableField::getTableId, tableId).notIn(GenTableField::getId, ids));
            }
            tableFieldService.saveOrUpdateBatch(tableFields, 100);
        } else {
            tableFieldService.remove(new QueryWrapper<GenTableField>().lambda().eq(GenTableField::getTableId, tableId));
        }
        return entity.updateById();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveWithFields(GenTable entity) {
        List<GenTableField> tableFields = entity.getTableFields();
        if (Tools.isNotEmpty(tableFields)) {
            tableFieldService.saveBatch(tableFields, 100);
        }
        return entity.insert();
    }
}
