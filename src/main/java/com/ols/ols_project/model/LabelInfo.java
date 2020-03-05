package com.ols.ols_project.model;

import lombok.*;

/**
 * 存储图片标注信息
 * @author yuyy
 * @date 20-2-25 下午4:40
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LabelInfo {
    private Double x;
    private Double y;
    private Double ex;
    private Double ey;
    private String name;
}
