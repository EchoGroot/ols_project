package com.ols.ols_project.model;

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
    private Integer id;

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
    private Timestamp releasetime;

    /**
     * 完成时间
     */
    private Timestamp finishtime;

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
    private String ext3;
}
