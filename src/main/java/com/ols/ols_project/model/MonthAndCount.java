package com.ols.ols_project.model;

import lombok.*;

/**
 * 历史审核信息的实体类
 * @author yuyy
 * @date 20-3-17 下午5:21
 */
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MonthAndCount {
    private String month;
    private String count;
}
