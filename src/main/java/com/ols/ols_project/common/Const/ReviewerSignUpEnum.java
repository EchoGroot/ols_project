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
public enum ReviewerSignUpEnum {
    NOT_HANDLED("待处理",0),
    PASS("通过",1),
    NOT_PASS("不通过",2);

    private String name;
    private Integer code;

    ReviewerSignUpEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    public static String getNameByCode(int code){
        String name = "";
        ReviewerSignUpEnum[] roleEnums = ReviewerSignUpEnum.values();
        for (ReviewerSignUpEnum roleEnum : roleEnums) {
            if(roleEnum.getCode()==code){
                name =  roleEnum.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        ReviewerSignUpEnum[] roleEnums = ReviewerSignUpEnum.values();
        for (ReviewerSignUpEnum roleEnum : roleEnums) {
            if(roleEnum.getName().equals(name)){
                code =  roleEnum.getCode();
                break;
            }
        }
        return code;
    }

}
