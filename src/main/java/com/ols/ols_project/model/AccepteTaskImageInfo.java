package com.ols.ols_project.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "taskImage")
@XmlType(propOrder = {
        "originalImage",
        "isLabeled",
        "isExample",
        "labeledInfo"
})
public class AccepteTaskImageInfo {
    private String originalImage;
    private Boolean isLabeled;
    private Boolean isExample;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullListAsEmpty})
    private List<LabelInfo> labeledInfo;
}
