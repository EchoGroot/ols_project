package com.ols.ols_project.common.Const;

import lombok.Getter;
import lombok.ToString;

/**
 * 用户角色枚举类
 * @author yuyy
 * @date 20-3-21 下午5:21
 */
@Getter
@ToString
public enum IsPassedEnum {
    NO("否",0),
    YES("是",1);

    private String name;
    private Integer code;

    IsPassedEnum(String name, Integer code){
        this.name = name;
        this.code=code;
    }

    public static String getNameByCode(int code){
        String name = "";
        IsPassedEnum[] enums = IsPassedEnum.values();
        for (IsPassedEnum enumTemp : enums) {
            if(enumTemp.getCode()==code){
                name =  enumTemp.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        IsPassedEnum[] enums = IsPassedEnum.values();
        for (IsPassedEnum enumTemp : enums) {
            if(enumTemp.getName().equals(name)){
                code =  enumTemp.getCode();
                break;
            }
        }
        return code;
    }

}
