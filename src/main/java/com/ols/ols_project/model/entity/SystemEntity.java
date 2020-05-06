package com.ols.ols_project.model.entity;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SystemEntity {
    /**
    * 消息编号
    * */
    private Long id;
    /**
     * 发布者者编号
     * */
    private Long release_user_id;
    /**
     * 接受者编号
     * */
    private Long accept_user_id;
    /**
     * 消息内容
     * */
    private String message;
    /**
     * 创建时间
     * */
    private Timestamp create_time;
    /**
     * 备用字段1
     * */
    private String ext1;
    /**
     * 备用字段2
     * */
    private String ext2;
    /**
     * 是否已查看
     * */
    private int ext3;
}
