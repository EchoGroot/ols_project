package com.ols.ols_project.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DayAndCount {
    private String day;
    private String count;
}
