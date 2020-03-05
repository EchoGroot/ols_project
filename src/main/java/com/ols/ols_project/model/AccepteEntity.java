package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * ols_accepte表的实体类
 * @author yuyy
 * @date 20-2-21 下午12:33
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccepteEntity {

    /**
     * 接受任务编号
     */
    private Integer id;

    /**
     * 接受者编号
     */
    private Integer user_id;

    /**
     * 任务编号
     */
    private Integer task_id;

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
