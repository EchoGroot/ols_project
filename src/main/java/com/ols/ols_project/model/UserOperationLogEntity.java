package com.ols.ols_project.model;

import lombok.*;

import java.sql.Timestamp;

/**
 * 对应ols_user_operation_log表的实体类
 * @author yuyy
 * @date 20-3-7 下午6:57
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOperationLogEntity {

    /**
     * 编号
     */
    private Long id;

    /**
     * 用户编号
     */
    private Long user_id;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 具体操作
     */
    private String operation;

    /**
     * 时间
     */
    private Timestamp time;

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
