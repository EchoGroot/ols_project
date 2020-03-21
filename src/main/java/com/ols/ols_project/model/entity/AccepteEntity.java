package com.ols.ols_project.model.entity;

import lombok.*;

import java.sql.Timestamp;

/**
 * ols_accept表的实体类
 * @author yuyy
 * @date 20-2-21 下午12:33
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccepteEntity {

    /**
     * 接受任务编号
     */
    private Long id;

    /**
     * 接受者编号
     */
    private Long user_id;

    /**
     * 任务编号
     */
    private Long task_id;

    /**
     * 接受时间
     */
    private Timestamp accept_time;

    /**
     * 提交时间
     */
    private Timestamp finish_time;

    /**
     * 接受状态
     */
    private Integer state;

    /**
     * 标注文件路径
     */
    private String url;

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
