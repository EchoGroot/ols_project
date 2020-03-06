package com.ols.ols_project.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.sql.Date;

/**
 * ols_user表的实体类的业务对象
 * 将一些属性转化
 * 例如role字段为2，转成审核者
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
public class UserEntityBo {

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
    private String role;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 备用字段1
     * 审核者账号是否批准
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

    /**
     * 没有密码的构造函数
     * @param id
     * @param name
     * @param birthday
     * @param sex
     * @param email
     * @param role
     * @param points
     * @param ext1
     * @param ext2
     * @param ext3
     */
    public UserEntityBo(Integer id, String name, Date birthday, String sex, String email, String role, Integer points, String ext1, String ext2, String ext3) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.email = email;
        this.role = role;
        this.points = points;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
    }
}
