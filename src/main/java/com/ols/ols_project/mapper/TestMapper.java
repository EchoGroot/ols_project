package com.ols.ols_project.mapper;

import com.ols.ols_project.model.TestUser;

import java.util.List;

/**
 * 测试基础功能的Mapper
 * @author yuyy
 * @date 20-2-9 下午8:08
 */
public interface TestMapper {
    List<TestUser> selAll();
}
