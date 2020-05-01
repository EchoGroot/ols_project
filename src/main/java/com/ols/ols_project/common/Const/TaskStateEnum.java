package com.ols.ols_project.common.Const;

import lombok.Getter;
import lombok.ToString;

/**
 * 任务状态枚举类
 * @author yuyy
 * @date 20-3-21 下午5:21
 */
@Getter
@ToString
public enum TaskStateEnum {
    FINISH("已完成",1),
    DELETE("已删除",2),
    INVALID("已失效",3),
    CHECK("审核中",4),
    RELEASE("已发布",5),
    NOT_PASSED_CHECK("未通过审核",6),
    NOT_LABEL_EXAMPLE("未标注示例",7);

    private String name;
    private Integer code;

    TaskStateEnum(String name, Integer code){
        this.name = name;
        this.code = code;
    }

    public static String getNameByCode(int code){
        String name = "";
        TaskStateEnum[] enums = TaskStateEnum.values();
        for (TaskStateEnum enumTemp : enums) {
            if(enumTemp.getCode()==code){
                name =  enumTemp.getName();
                break;
            }
        }
        return name;
    }

    public static Integer getCodeByName(String name){
        Integer code = null;
        TaskStateEnum[] enums = TaskStateEnum.values();
        for (TaskStateEnum enumTemp : enums) {
            if(enumTemp.getName().equals(name)){
                code =  enumTemp.getCode();
                break;
            }
        }
        return code;
    }

}
