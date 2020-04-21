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
public class RewardAndPunishmentEnityBo {
    /**
     * 主键
     * 编号
     */
    private Long id;
    /**
     * 惩罚类型
     */
    private int type;
    /**
     * 用户编号
     */
    private Long user_id;
    /**
     * 奖惩信息
     */
    private String information;
    /**
     * 备用字段1
     */
    private String ext1;
    /**
     * 备用字段2
     */
    private String ext2;
    /**
     * 创建时间
     */
    private Timestamp create_time;
}
