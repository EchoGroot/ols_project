package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * 已接受的任务
 * @author yuyy
 * @date 20-2-20 下午2:20
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AcceptTaskBo {
    /**
     * 任务编号
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务分值
     */
    private Integer points;

    /**
     * 状态
     */
    private String taskState;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 发布时间
     */
    private Timestamp releaseTime;

    /**
     * 发布者名称
     */
    private String releaseName;

    /**
     * 接受者数量
     */
    private Integer acceptNum;

    /**
     * 接受时间
     */
    private Timestamp acceptTime;

    /**
     * 提交时间
     */
    private Timestamp finishTime;

    /**
     * 接受状态
     */
    private String acceptState;
}