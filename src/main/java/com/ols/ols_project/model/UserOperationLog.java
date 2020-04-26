package com.ols.ols_project.model;

import lombok.*;
import lombok.experimental.Accessors;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)

public class UserOperationLog {
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
    private String type;

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
