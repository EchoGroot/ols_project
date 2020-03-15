package com.ols.ols_project.model;

import lombok.*;

import java.util.List;

/**
 * 处理已接受任务表里的图片标注URL
 * @author yuyy
 * @date 20-2-25 下午8:01
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccepteImageUrl {
    private List<AccepteTaskImageInfo> taskImage;
    private List<String> labelName;
}
