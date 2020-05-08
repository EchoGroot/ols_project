package com.ols.ols_project.model;

import lombok.*;

/**
 * 性别信息实体类
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SexAndCount {
    private String sex;
    private String count;
}
