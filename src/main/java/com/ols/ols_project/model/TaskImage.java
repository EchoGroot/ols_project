package com.ols.ols_project.model;

import lombok.*;

import java.util.List;

/**
 * 处理已接受任务表里的图片标注URL
 * @author yuyy
 * @date 20-2-24 下午7:19
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskImage {
    private List<String> labelName;
    private List<TaskImageBean> taskImage;

    public List<String> getLabelName() {
        return labelName;
    }

    public void setLabelName(List<String> labelName) {
        this.labelName = labelName;
    }

    public List<TaskImageBean> getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(List<TaskImageBean> taskImage) {
        this.taskImage = taskImage;
    }

    public static class TaskImageBean {
        /**
         * isExample : true
         * isLabeled : true
         * labeledInfo : [{"ex":578.4375,"ey":517.83,"name":"渗水","x":194.06249999999997,"y":126.3},{"ex":345.9375,"ey":728.33,"name":"生锈","x":182.8125,"y":555.72},{"ex":638.4375,"ey":326.27500000000003,"name":"生锈","x":315.9375,"y":134.72},{"ex":820.3125,"ey":446.26000000000005,"name":"裂缝","x":475.3125,"y":216.815},{"ex":325.3125,"ey":393.63500000000005,"name":"裂缝","x":173.4375,"y":261.02},{"ex":1292.8125,"ey":458.89000000000004,"name":"安全隐患","x":1052.8125,"y":189.45000000000002}]
         * originalImage : 1812_XF100251.jpg
         */

        private boolean isExample;
        private boolean isLabeled;
        private String originalImage;
        private List<LabeledInfoBean> labeledInfo;

        public boolean isIsExample() {
            return isExample;
        }

        public void setIsExample(boolean isExample) {
            this.isExample = isExample;
        }

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
             * ex : 578.4375
             * ey : 517.83
             * name : 渗水
             * x : 194.06249999999997
             * y : 126.3
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
