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
 * @author yangshengce
 * @date 20-5-2 下午4:25
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "taskImage")
@XmlType(propOrder = {
        "originalDoc",
        "isLabeled",
        "isExample",
        "labeledInfo"
})
public class AcceptTaskDocInfo {
    private String originalDoc;
    private Boolean isLabeled;
    private Boolean isExample;
    @JSONField(serialzeFeatures= {SerializerFeature.WriteMapNullValue})
    private String labeledInfo;
}
