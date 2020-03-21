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
        RoleEnum[] roleEnums = RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {
            if(roleEnum.getCode()==code){
                name =  roleEnum.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        RoleEnum[] roleEnums = RoleEnum.values();
        for (RoleEnum roleEnum : roleEnums) {
            if(roleEnum.getName().equals(name)){
                code =  roleEnum.getCode();
                break;
            }
        }
        return code;
    }

}
