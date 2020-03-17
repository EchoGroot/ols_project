package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * 完成审核的任务实体类
 * @author yuyy
 * @date 20-3-17 上午10:28
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FinishCheckTask {

    /**
     * 任务ID
     */
    private Integer taskId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务发布时间
     */
    private Timestamp releaseTime;

    /**
     * 任务发布者用户名
     */
    private String releaseUserName;

    /**
     * 审核编号
     */
    private Integer judgeId;

    /**
     * 是否通过审核
     */
    private Integer isPassed;

    /**
     * 审核答复信息
     */
    private String message;

    /**
     * 审核时间
     */
    private Timestamp judgeTime;
}
