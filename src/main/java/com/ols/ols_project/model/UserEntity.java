package com.ols.ols_project.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * ols_user表的实体类
 * @author yuyy
 * @date 20-2-17 下午4:33
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserEntity {

    /**
     * 主键
     * 用户编号
     */
    private Integer id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色
     */
    private Integer role;

    /**
     * 积分
     */
    private Integer points;

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
