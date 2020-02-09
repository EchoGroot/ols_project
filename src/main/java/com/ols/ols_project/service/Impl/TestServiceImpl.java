package com.ols.ols_project.service.Impl;

import com.ols.ols_project.mapper.TestMapper;
import com.ols.ols_project.model.TestUser;
import com.ols.ols_project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuyy
 * @date 20-2-9 下午8:57
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Override
    public List<TestUser> selAll() {
        return testMapper.selAll();
    }
}
