package org.jeeasy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SysUserStatusEnum: 系统用户状态
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/22 2:10
 */
@Getter
@AllArgsConstructor
public enum SysUserStatusEnum {

    // 正常状态
    NORMAL(1, "正常"),
    // 冻结状态
    FREEZE(0, "冻结"),
    ;

//    @EnumValue
    private final Integer value;
    private final String text;

//    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
//    public static SysUserStatusEnum getByValue(List<?> listVal) {
////        if(value.size() == 2 && EnumObject.class.getName().equals(value.get(0))){
////            EnumObject enumObject = (EnumObject)value.get(1);
////            return EnumUtil.likeValueOf(SysUserStatusEnum.class, enumObject.getValue());
////        }
//        Object value = BeanUtil.getFieldValue(listVal.get(1), "value");
//        return EnumUtil.likeValueOf(SysUserStatusEnum.class, value);
//    }
//
//    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
//    public static SysUserStatusEnum getByValue1(@JsonProperty("status") List<?> listVal) {
////        if(value.size() == 2 && EnumObject.class.getName().equals(value.get(0))){
////            EnumObject enumObject = (EnumObject)value.get(1);
////            return EnumUtil.likeValueOf(SysUserStatusEnum.class, enumObject.getValue());
////        }
//        Object value = BeanUtil.getFieldValue(listVal.get(1), "value");
//        return EnumUtil.likeValueOf(SysUserStatusEnum.class, value);
//    }
//
//    @JsonValue
//    public JSON toJson(){
//        return JSONUtil.parse(new EnumObject(this.value, this.text));
//    }

//    @Override
//    public String toString() {
////        String jsonStr = JSONUtil.toJsonStr(this);
////        return jsonStr;
//        return JSONUtil.toJsonStr(new EnumObject(this.value, this.text));
////        return value.toString();
//    }

//    @JsonCreator
//    SysUserStatusEnum(Integer value, String text) {
//        this.value = value;
//        this.text = text;
//    }


//    /**
//     * TODO 自定义返回前端数据--> json数据-- {"value":2,"desc":"菜单"}
//     *
//     * @JsonValue 作用： 将该返回的参数序列化 --> 枚举返回参数
//     * @JsonIgnore 作用：在实体类向前台返回数据时用来忽略不想传递给前台的属性或接口, 因此用来过滤，使前端传递参数的时候就不会使用该方法进行反序列化枚举对象了
//     */
//    @JsonValue(value = true)
//    @JsonIgnore
//    public JSON respEnum() {
//        return JSONUtil.parseObj("{\"value\":" + this.value + ",\"text\":\"" + this.text + "\"}");
//    }
}
