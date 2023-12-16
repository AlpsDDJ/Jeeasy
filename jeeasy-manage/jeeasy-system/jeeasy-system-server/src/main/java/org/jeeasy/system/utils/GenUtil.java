package org.jeeasy.system.utils;

import cn.hutool.core.util.ClassUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jeeasy.common.core.annotation.dict.Dict;
import org.jeeasy.generate.domain.GenModule;

import java.util.Arrays;
import java.util.Objects;

public class GenUtil {
    public static void generateTsClass(Class<?> clazz) {
        String[] ignore = {"id", "createBy", "createTime", "updateBy", "updateTime", "delFlag", "sysOrgCode", "serialVersionUID"};
        String clazzSimpleName = clazz.getSimpleName();
        System.out.println("@Model()\n" +
                "export default class " + clazzSimpleName + " extends BaseModel {\n");

        Arrays.stream(ClassUtil.getDeclaredFields(clazz)).forEach(field -> {
            String fieldName = field.getName();
            String typeName = field.getType().getSimpleName().toLowerCase();
            Schema schema = field.getAnnotation(Schema.class);
            String label = fieldName;
            if (Objects.nonNull(schema)) {
                label = schema.description();
            }
            Dict dict = field.getAnnotation(Dict.class);
            String dictCode = "";
            if (Objects.nonNull(dict)) {
                dictCode = dict.dictCode();
                if (StringUtils.isEmpty(dictCode)) {
                    dictCode = dict.dictEnum().getSimpleName().replace("Enum", "");
                }
            }

            if (!ArrayUtils.contains(ignore, fieldName)) {
                String tsType;
                switch (typeName) {
                    case "integer":
                    case "int":
                    case "long":
                    case "double":
                    case "float":
                        tsType = "number";
                        break;
                    case "boolean":
                        tsType = "boolean";
                        break;
                    default:
                        tsType = "string";
                        break;
                }
                System.out.println("@Field('" + label + "')");
                if (StringUtils.isNoneEmpty(dictCode)) {
                    System.out.println("@Field.Dict('" + dictCode + "')");
                }
                if ("number".equals(tsType)) {
                    System.out.println("@Field.DataType(FormDataType.NUMBER)");
                }
                System.out.println(fieldName + "?: " + tsType + "\n");
            }
        });

        System.out.println("}");
    }

    public static void main(String[] args) {
        GenUtil.generateTsClass(GenModule.class);
    }

}
