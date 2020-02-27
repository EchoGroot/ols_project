package com.ols.ols_project.model;

import lombok.*;

/**
 * 处理已接受任务表里的图片标注URL
 * @author yuyy
 * @date 20-2-24 下午7:19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskImage {
    private String[] imageUrl;
}
