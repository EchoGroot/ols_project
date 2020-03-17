package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * ols_judge表的实体类
 * @author yuyy
 * @date 20-3-16 下午4:39
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JudgeEntity {
    /**
     * 审核任务编号
     */
    private Integer id;

    /**
     * 审核者编号
     */
    private Integer user_id;

    /**
     * 任务编号
     */
    private Integer task_id;

    /**
     * 是否通过
     */
    private Integer ispassed;

    /**
     * 是否是第一次查看
     */
    private Integer isfirst;

    /**
     * 答复信息
     */
    private String message;

    /**
     * 审核时间
     */
    private Timestamp judge_time;

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
