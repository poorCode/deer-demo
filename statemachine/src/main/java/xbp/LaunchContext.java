package xbp;

import java.time.LocalDate;

/**
 * @author deer
 * @date 2021-12-16
 */
public class LaunchContext {
    /**
     * 启动日期
     */
    private LocalDate startDate;
    /**
     * 任务状态
     */
    private LaunchStatus status;
    /**
     * 是否开启试验
     */
    private boolean openTest;
    /**
     * 流量百分比
     */
    private int flowPercent;


    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LaunchStatus getStatus() {
        return status;
    }

    public void setStatus(LaunchStatus status) {
        this.status = status;
    }

    public boolean isOpenTest() {
        return openTest;
    }

    public void setOpenTest(boolean openTest) {
        this.openTest = openTest;
    }

    public int getFlowPercent() {
        return flowPercent;
    }

    public void setFlowPercent(int flowPercent) {
        this.flowPercent = flowPercent;
    }
}
