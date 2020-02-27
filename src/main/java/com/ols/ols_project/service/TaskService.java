package com.ols.ols_project.service;

import com.ols.ols_project.model.AcceptTask;
import com.ols.ols_project.model.LabelInfo;

import java.util.List;

/**
 * @author yuyy
 * @date 20-2-24 下午7:05
 */
public interface TaskService {

    String getImageListByTaskId(int taskId);

    AcceptTask getAccepteImageListByAccepteId(int accepteId);

    int storeImageLabelInfo(int accepteTaskId, List<LabelInfo> labelInfos, String imageUrlParam);
}
