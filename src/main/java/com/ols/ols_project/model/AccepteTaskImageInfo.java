package com.ols.ols_project.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.*;

import java.util.List;

/**
 * 处理已接受任务表里的图片标注URL
 * @author yuyy
 * @date 20-2-25 下午8:05
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccepteTaskImageInfo {
    private String originalImage;
    private Boolean isLabeled;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty})
    private List<LabelInfo> labeledInfo;
}
