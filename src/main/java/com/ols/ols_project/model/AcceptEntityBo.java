package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AcceptEntityBo {

    /**
     * 接受任务编号
     */
    private Long acceptId;

    /**
     * 接受者编号
     */
    private Long acceptUserId;
    /**
     * 接受者名
     */
    private String acceptUserName;

    /**
     * 任务编号
     */
    private Long taskId;

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
    private String state;

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
