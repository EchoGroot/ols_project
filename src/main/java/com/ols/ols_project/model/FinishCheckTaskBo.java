package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * 完成审核的任务，面向业务的实体类
 * @author yuyy
 * @date 20-3-17 上午10:28
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FinishCheckTaskBo {

    /**
     * 任务ID
     */
    private Long taskId;

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
    private Long judgeId;

    /**
     * 是否通过审核
     */
    private String isPassed;

    /**
     * 审核答复信息
     */
    private String message;

    /**
     * 审核时间
     */
    private Timestamp judgeTime;
}
