package com.ols.ols_project.model;

import lombok.*;

/**
 * 返回数据给前端的信息实体类
 * @author yuyy
 * @date 20-2-19 下午2:39
 */
@Getter
@Setter
@ToString
public class Meta {
    private String status;
    private String msg;

    public Meta(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Meta() {
    }
}
