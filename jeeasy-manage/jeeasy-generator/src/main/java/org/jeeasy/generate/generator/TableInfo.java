package org.jeeasy.generate.generator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.NamingCase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.generate.domain.GenTable;
import org.jeeasy.generate.domain.GenTableField;
import org.jeeasy.generate.domain.GenTableIndex;
import org.jeeasy.generate.domain.dto.GeneratorDto;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class TableInfo extends GenTable {
    private CaseName caseName;
    private String date;
    private String author;
    private String apiPath;
    private String permission;
    private GeneratorDto.GeneratorFile entity;
    private GeneratorDto.GeneratorFile mapper;
    private GeneratorDto.GeneratorFile mapperXml;
    private GeneratorDto.GeneratorFile service;
    private GeneratorDto.GeneratorFile serviceImpl;
    private GeneratorDto.GeneratorFile controller;


    private List<TableField> fields;
    private List<TableIndex> indexs;

    public TableInfo(GenTable table, GeneratorDto generatorDto) {
        BeanUtils.copyProperties(table, this);
        String tableName = table.getName();
        this.caseName = new CaseName(tableName);
        this.date = Tools.getTime();

        this.author = StringUtils.defaultIfBlank(generatorDto.getAuthor(), "wei.yang");
        this.fields = table.getTableFields().stream().map(TableField::new).toList();
        this.indexs = table.getTableIndexs().stream().map(TableIndex::new).toList();

        generatorDto.getFiles().forEach(item -> {
            if (Objects.nonNull(BeanUtils.getPropertyDescriptor(this.getClass(), item.getType()))) {
                BeanUtil.setFieldValue(this, item.getType(), item);
            }
        });
        String moduleName = tableName.substring(0, tableName.indexOf("_"));
        String simpleName = NamingCase.toCamelCase(tableName.substring(tableName.indexOf("_") + 1));

        this.apiPath = String.format("/%s/%s", moduleName, simpleName);
        this.permission = String.format("%s-%s", moduleName, simpleName);
    }

    public Context toContext() {
        return new VelocityContext(BeanUtil.beanToMap(this));
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TableField extends GenTableField {
        private CaseName caseName;
        private String tsType;
        private boolean ignore;
        private String hiddenType;
        private String disabledType;

        private static final String[] ignoreFields = {"id", "createBy", "createTime", "updateBy", "updateTime", "delFlag", "sysOrgCode", "serialVersionUID"};

        public TableField(GenTableField field) {
            BeanUtils.copyProperties(field, this);
            this.caseName = new CaseName(field.getFieldName());
            this.ignore = ArrayUtils.contains(ignoreFields, field.getFieldName());
            if (!this.ignore) {
                toTsType();
                toHiddenType();
                toDisabledType();
            }
        }

        private void toDisabledType() {
            List<String> disabledTypes = new ArrayList<>();
            int disableOnTableEdit = Tools.isEmpty(this.getDisableOnTableEdit()) ? 0 : this.getDisableOnTableEdit();
            int disableOnAdd = Tools.isEmpty(this.getDisableOnAdd()) ? 0 : this.getDisableOnAdd();
            int disableOnEdit = Tools.isEmpty(this.getDisableOnEdit()) ? 0 : this.getDisableOnEdit();
            int show = disableOnTableEdit + disableOnAdd + disableOnEdit;
            if (show == 0) {
                disabledTypes.add("false");
            } else {
                if (disableOnTableEdit == 1) {
                    disabledTypes.add("'editTable'");
                }
                if (disableOnAdd + disableOnEdit == 2) {
                    disabledTypes.add("'form'");
                } else {
                    if (disableOnAdd == 1) {
                        disabledTypes.add("'add'");
                    }
                    if (disableOnEdit == 1) {
                        disabledTypes.add("'edit'");
                    }
                }
            }
            this.disabledType = CollectionUtil.join(disabledTypes, ", ");
        }

        /**
         * 将控件类型设置为隐藏类型
         */
        private void toHiddenType() {
            // 创建一个存储隐藏类型的列表
            List<String> hiddenTypes = new ArrayList<>();

            // 获取showTable、showSearch、showAdd、showEdit的值，如果为空则设置为0
            int showTable = Tools.isEmpty(this.getShowTable()) ? 0 : this.getShowTable();
            int showSearch = Tools.isEmpty(this.getShowSearch()) ? 0 : this.getShowSearch();
            int showAdd = Tools.isEmpty(this.getShowAdd()) ? 0 : this.getShowAdd();
            int showEdit = Tools.isEmpty(this.getShowEdit()) ? 0 : this.getShowEdit();

            // 打印日志信息
            log.debug("{} ---> showTable:{}, showSearch:{}, showAdd:{}, showEdit {}", this.caseName.getCamel(), showTable, showSearch, showAdd, showEdit);

            // 计算show的值
            int show = showTable + showSearch + showAdd + showEdit;

            // 根据show的值设置隐藏类型
            if (show == 0) {
                hiddenTypes.add("true");
            } else if (show == 4) {
                hiddenTypes.add("false");
            } else {
                if (showTable == 0) {
                    hiddenTypes.add("'list'");
                }
                if (showSearch == 0) {
                    hiddenTypes.add("'search'");
                }
                if (showAdd + showEdit == 0) {
                    hiddenTypes.add("'form'");
                } else {
                    if (showAdd == 0) {
                        hiddenTypes.add("'add'");
                    }
                    if (showEdit == 0) {
                        hiddenTypes.add("'edit'");
                    }
                }
            }

            // 将隐藏类型列表转换为字符串，并设置join为该字符串
            String join = CollectionUtil.join(hiddenTypes, ", ");
            log.debug("hiddenType ---> {}", join);
            this.hiddenType = join;
        }


        private void toTsType() {
            String javaType = getJavaType();
            if (Tools.isEmpty(javaType)) {
                this.tsType = "string";
                return;
            }
            this.tsType = switch (javaType) {
                case "integer", "int", "long", "double", "float" -> "number";
                case "boolean" -> "boolean";
                default -> "string";
            };
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class TableIndex extends GenTableIndex {
        public TableIndex(GenTableIndex index) {
            BeanUtils.copyProperties(index, this);
        }
    }

}
