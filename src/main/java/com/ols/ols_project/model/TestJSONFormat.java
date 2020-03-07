package com.ols.ols_project.model;

import java.util.List;

/**
 * 测试JSONFormat
 * @author yuyy
 * @date 20-3-7 下午2:03
 */
public class TestJSONFormat {
    private List<TaskImageBean> taskImage;

    public List<TaskImageBean> getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(List<TaskImageBean> taskImage) {
        this.taskImage = taskImage;
    }

    public static class TaskImageBean {
        /**
         * isLabeled : true
         * labeledInfo : [{"ex":0.582716049382716,"ey":0.6141975308641975,"name":"123","x":0.5530864197530864,"y":0.37962962962962965}]
         * originalImage : 1812_XF100251.jpg
         */

        private boolean isLabeled;
        private String originalImage;
        private List<LabeledInfoBean> labeledInfo;

        public boolean isIsLabeled() {
            return isLabeled;
        }

        public void setIsLabeled(boolean isLabeled) {
            this.isLabeled = isLabeled;
        }

        public String getOriginalImage() {
            return originalImage;
        }

        public void setOriginalImage(String originalImage) {
            this.originalImage = originalImage;
        }

        public List<LabeledInfoBean> getLabeledInfo() {
            return labeledInfo;
        }

        public void setLabeledInfo(List<LabeledInfoBean> labeledInfo) {
            this.labeledInfo = labeledInfo;
        }

        public static class LabeledInfoBean {
            /**
             * ex : 0.582716049382716
             * ey : 0.6141975308641975
             * name : 123
             * x : 0.5530864197530864
             * y : 0.37962962962962965
             */

            private double ex;
            private double ey;
            private String name;
            private double x;
            private double y;

            public double getEx() {
                return ex;
            }

            public void setEx(double ex) {
                this.ex = ex;
            }

            public double getEy() {
                return ey;
            }

            public void setEy(double ey) {
                this.ey = ey;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getX() {
                return x;
            }

            public void setX(double x) {
                this.x = x;
            }

            public double getY() {
                return y;
            }

            public void setY(double y) {
                this.y = y;
            }
        }
    }
}
