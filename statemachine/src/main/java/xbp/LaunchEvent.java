package xbp;

/**
 * @author deer
 * @date 2021-12-16
 */
public enum LaunchEvent {
    /**
     * 提交
     */
    SUBMIT(0),
    /**
     * 审核通过
     */
    APPROVED(1),
    /**
     * 审核驳回
     */
    REJECT_APPROVED(2),
    /**
     * 投放未开始
     */
    NOT_STARTED(3),
    /**
     * 开启试验
     */
    OPEN_TEST(4),
    /**
     * 未开启试验
     */
    NOT_OPEN_TEST(5),
    /**
     * 调整流量
     */
    ADJUST_FLOW(7),
    /**
     * 触发下线
     */
    OFFLINE(8);

    private final int value;

    LaunchEvent(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
