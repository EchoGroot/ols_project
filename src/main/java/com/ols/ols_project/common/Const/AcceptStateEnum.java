package com.ols.ols_project.common.Const;

import lombok.Getter;
import lombok.ToString;

/**
 * 接受任务状态枚举类
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
        String name = "";
        AcceptStateEnum[] enums = AcceptStateEnum.values();
        for (AcceptStateEnum enumTemp : enums) {
            if(enumTemp.getCode()==code){
                name =  enumTemp.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        AcceptStateEnum[] enums = AcceptStateEnum.values();
        for (AcceptStateEnum enumTemp : enums) {
            if(enumTemp.getName().equals(name)){
                code =  enumTemp.getCode();
                break;
            }
        }
        return code;
    }

}
