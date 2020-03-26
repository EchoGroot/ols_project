package com.ols.ols_project.service;

/**
 * 向数据库添加测试数据
 * @author yuyy
 * @date 20-2-17 下午4:20
 */
public interface CreateTestDataService {
    void createTestDataForOlsUser(long userIdStart);
    void createTestDataForOlsTask();
    void createTestDataForOlsAccepte();

    void createTestDataForOlsJudge(long userId, long taskId);
}
