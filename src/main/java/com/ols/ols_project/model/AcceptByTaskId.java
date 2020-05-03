package com.ols.ols_project.model;

import java.sql.Timestamp;

/**
 * 通过任务id查询出对应的接受任务
 */
public class AcceptByTaskId {
    private String name;
    private Long  id;
    private Long task_id;
    private Timestamp accept_time;
    private Timestamp finish_time;
    private Integer state;
    private String url;
    private String ext1;
    private String ext2;
    private String ext3;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTask_id(Long task_id) {
        this.task_id = task_id;
    }

    public void setAccept_time(Timestamp accept_time) {
        this.accept_time = accept_time;
    }

    public void setFinish_time(Timestamp finish_time) {
        this.finish_time = finish_time;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }

    public void setExt(String ext3) {
        this.ext3 = ext3;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getTask_id() {
        return task_id;
    }

    public Timestamp getAccept_time() {
        return accept_time;
    }

    public Timestamp getFinish_time() {
        return finish_time;
    }

    public Integer getState() {
        return state;
    }

    public String getUrl() {
        return url;
    }

    public String getExt1() {
        return ext1;
    }

    public String getExt2() {
        return ext2;
    }

    public String getExt3() {
        return ext3;
    }

    public AcceptByTaskId() {
    }

    public AcceptByTaskId(String name, Long id, Long task_id, Timestamp accept_time, Timestamp finish_time, Integer state, String url, String ext1, String ext2, String ext3) {
        this.name = name;
        this.id = id;
        this.task_id = task_id;
        this.accept_time = accept_time;
        this.finish_time = finish_time;
        this.state = state;
        this.url = url;
        this.ext1 = ext1;
        this.ext2 = ext2;
        this.ext3 = ext3;
    }
}
