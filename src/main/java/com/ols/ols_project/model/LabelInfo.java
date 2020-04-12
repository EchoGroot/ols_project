package com.ols.ols_project.model;

import lombok.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

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
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "labeledInfo")
@XmlType(propOrder = {
        "x",
        "y",
        "ex",
        "ey",
        "name"
})
public class LabelInfo {
    private Double x;
    private Double y;
    private Double ex;
    private Double ey;
    private String name;
}
