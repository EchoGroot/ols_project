package com.ols.ols_project.model.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * ols_task表的实体类
 * @author yuyy
 * @date 20-2-17 下午8:24
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class TaskEntity {

    /**
     * 任务编号
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 原始文件路径
     */
    private String url;

    /**
     * 详细信息
     */
    private String information;

    /**
     * 任务分值
     */
    private Integer points;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 文件类型
     */
    private Integer type;

    /**
     * 发布时间
     */
    private Timestamp release_time;

    /**
     * 完成时间
     */
    private Timestamp finish_time;

    /**
     *发布者编号
     */
    private Long release_user_id;

    /**
     *接受者数量
     */
    private Integer accept_num;

    /**
     *采纳的接受任务编号
     */
    private Long adopt_accept_id;

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
}
