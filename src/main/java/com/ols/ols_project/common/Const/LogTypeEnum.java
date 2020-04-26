package com.ols.ols_project.common.Const;

import lombok.Getter;
import lombok.ToString;
@Getter
@ToString
public enum LogTypeEnum {
    REGISTER("注册",0),
    LOGIN("登录",1),
    OTHER("其他",2);
    private String name;
    private Integer code;

    LogTypeEnum(String name,Integer code){
        this.name = name;
        this.code = code;
    }

    public static String getNameByCode(int code){
        String name = "";
        LogTypeEnum[] logTypeEnums = LogTypeEnum.values();
        for (LogTypeEnum logTypeEnum : logTypeEnums) {
            if(logTypeEnum.getCode()==code){
                name = logTypeEnum.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        LogTypeEnum[] logTypeEnums = LogTypeEnum.values();
        for (LogTypeEnum logTypeEnum : logTypeEnums) {
            if(logTypeEnum.getName().equals(name)){
                code =  logTypeEnum.getCode();
                break;
            }
        }
        return code;
    }
}
