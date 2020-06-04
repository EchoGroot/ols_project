package com.ols.ols_project.model;

import com.alibaba.fastjson.JSONObject;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * 处理已接受任务表里的图片标注URL
 * @author yangshengce
 * @date 20-5-4 下午2:21
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
//控制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标注）字段到XML。
@XmlAccessorType(XmlAccessType.FIELD)
// XML文件中的根标识
@XmlRootElement(name = "imageLabel")
// 控制JAXB 绑定类中属性和字段的排序
@XmlType(propOrder = {
        "taskDoc",
        "labelName"
})
@XmlSeeAlso(JSONObject.class)
public class AcceptDocUrl {
    private List<AcceptTaskDocInfo> taskDoc;
    private List<String> labelName;

}
