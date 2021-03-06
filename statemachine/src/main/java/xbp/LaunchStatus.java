package xbp;

/**
 * @author deer
 * @date 2021-12-16
 */
public enum LaunchStatus {
    /**
     * 待提交
     */
    NOT_SUBMITTED(0),
    /**
     * 待审核
     */
    NOT_APPROVED(1),
    /**
     * 审核驳回
     */
    REJECT_APPROVED(2),
    /**
     * 待开始
     */
    NOT_STARTED(3),
    /**
     * 灰度中
     */
    GRAYSCALE(4),
    /**
     * 已全量
     */
    ALL_FLOW(5),
    /**
     * 已下线
     */
    OFFLINE(6);

    private final int value;

    LaunchStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
