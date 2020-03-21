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
public enum AcceptStateEnum {
    NOT_FINISH("未完成",0),
    FINISH("已提交",1),
    ADOPT("已采纳",2),
    NOT_ADOPT("未采纳",3),
    INVALID("已失效",4);

    private String name;
    private Integer code;

    AcceptStateEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    public static String getNameByCode(int code){
        String name = "roleCode不存在";
        AcceptStateEnum[] roleEnums = AcceptStateEnum.values();
        for (AcceptStateEnum roleEnum : roleEnums) {
            if(roleEnum.getCode()==code){
                name =  roleEnum.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        AcceptStateEnum[] roleEnums = AcceptStateEnum.values();
        for (AcceptStateEnum roleEnum : roleEnums) {
            if(roleEnum.getName().equals(name)){
                code =  roleEnum.getCode();
                break;
            }
        }
        return code;
    }

}
