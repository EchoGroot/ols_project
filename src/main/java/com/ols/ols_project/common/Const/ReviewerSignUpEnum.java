package com.ols.ols_project.common.Const;

import lombok.Getter;
import lombok.ToString;

/**
 * 审核者账号注册枚举类
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
        ReviewerSignUpEnum[] enums = ReviewerSignUpEnum.values();
        for (ReviewerSignUpEnum enumTemp : enums) {
            if(enumTemp.getCode()==code){
                name =  enumTemp.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        ReviewerSignUpEnum[] enums = ReviewerSignUpEnum.values();
        for (ReviewerSignUpEnum enumTemp : enums) {
            if(enumTemp.getName().equals(name)){
                code =  enumTemp.getCode();
                break;
            }
        }
        return code;
    }

}
