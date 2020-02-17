package com.ols.ols_project.model;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author yuyy
 * @date 20-2-9 下午8:08
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)//产生的setter返回的this而不是void
public class TestUser {
    private Integer id;
    private String name;
}
