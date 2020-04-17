package com.ols.ols_project.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
        * ols_message表的实体类
        * @author zs
        * @data 2020-04-10
        */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class MessageEnityBo {
    /**
     * 主键
     * 消息编号
     */
    private Long id;
    /**
     * 举报者编号
     */
    private Long user_id;
    /**
     * 举报的任务编号
     */
    private Long task_id;
    /**
     * 举报信息
     */
    private String message;
    /**
     * 是否处理
     */
    private int ishandled;
    /**
     * 答复信息
     */
    private String response;
    /**
     * 是否第一次查看
     */
    private int isfirst;
    /**
     * 备用字段1
     */
    private String ext1;
    /**
     * 备用字段2
     */
    private String ext2;
    /**
     * 备用字段3
     */
    private int ext3;
    /**
     * 创建时间
     */
    private Timestamp create_time;
}
