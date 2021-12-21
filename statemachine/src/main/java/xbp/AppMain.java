package xbp;

import com.alibaba.cola.statemachine.Action;
import com.alibaba.cola.statemachine.Condition;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;

import java.time.LocalDate;
import java.util.Scanner;


/**
 * @author deer
 * @date 2021-12-16
 */
public class AppMain {
    public static void main(String[] args) {
        buildStateMachine();
        String machineId = "launch";
        StateMachine<LaunchStatus, LaunchEvent, LaunchContext> stateMachine = StateMachineFactory.get(machineId);
        LaunchContext launchContext = new LaunchContext();
        launchContext.setStatus(LaunchStatus.NOT_SUBMITTED);
        launchContext.setStartDate(LocalDate.of(2021, 12, 21));
        launchContext.setOpenTest(true);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            switch (launchContext.getStatus()) {
                case NOT_SUBMITTED: {
                    System.out.println("当前状态是未提交，是否提交(yes/no)：");
                    String text = scanner.nextLine().trim();
                    if ("yes".equalsIgnoreCase(text)) {
                        stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.SUBMIT, launchContext);
                    }
                    if ("quit".equalsIgnoreCase(text)) {
                        return;
                    }
                    break;
                }
                case NOT_APPROVED: {
                    System.out.println("当前状态是未认证，是否认证(yes/no)：");
                    String text = scanner.nextLine().trim();
                    if ("yes".equalsIgnoreCase(text)) {
                        System.out.println("是否到达投放时间(yes/no)：");
                        String text2 = scanner.nextLine().trim();
                        if ("yes".equalsIgnoreCase(text2)) {
                            System.out.println("是否开启试验(yes/no)：");
                            String text3 = scanner.nextLine().trim();
                            if ("yes".equalsIgnoreCase(text3)) {
                                stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.OPEN_TEST, launchContext);
                            } else if ("no".equalsIgnoreCase(text3)){
                                stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.NOT_OPEN_TEST, launchContext);
                            }
                        } else if ("no".equalsIgnoreCase(text2)){
                            stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.NOT_STARTED, launchContext);
                        }
                    } else if ("no".equalsIgnoreCase(text)){
                        stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.REJECT_APPROVED, launchContext);
                    }
                    if ("quit".equalsIgnoreCase(text)) {
                        return;
                    }
                    break;
                }
                case REJECT_APPROVED: {
                    System.out.println("当前状态是审核驳回，即将退出");
                    return;
                }
                case NOT_STARTED: {
                    System.out.println("当前状态是未开始，是否下线(yes/no)：");
                    String text = scanner.nextLine().trim();
                    if ("yes".equalsIgnoreCase(text)) {
                        stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.APPROVED, launchContext);
                    }
                    if ("quit".equalsIgnoreCase(text)) {
                        return;
                    }
                    break;
                }
                case GRAYSCALE: {
                    System.out.println("选择下线或者调整流量？(1:下线,2:调整流量)");
                    String text = scanner.nextLine().trim();
                    if ("1".equalsIgnoreCase(text)) {
                        System.out.println("当前状态是灰度中，是否下线(yes/no)：");
                        String text2 = scanner.nextLine().trim();
                        if ("yes".equalsIgnoreCase(text2)) {
                            stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.OFFLINE, launchContext);
                        }
                    } else if ("2".equalsIgnoreCase(text)) {
                        System.out.println("当前状态是灰度中，调整流量至(1——100)：");
                        String text2 = scanner.nextLine().trim();
                        launchContext.setFlowPercent(Integer.parseInt(text2));
                        stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.ADJUST_FLOW, launchContext);
                    }
                    break;
                }
                case ALL_FLOW: {
                    System.out.println("当前状态是已全量，是否下线(yes/no)：");
                    String text = scanner.nextLine().trim();
                    if ("yes".equalsIgnoreCase(text)) {
                        stateMachine.fireEvent(launchContext.getStatus(), LaunchEvent.OFFLINE, launchContext);
                    }
                    break;
                }
                case OFFLINE: {
                    System.out.println("当前状态是下线...");
                    return;
                }
                default: {
                    return;
                }
            }
            /*System.out.println("是否退出(yes/no)：");
            String text = scanner.nextLine().trim();
            if ("yes".equalsIgnoreCase(text)) {
                break;
            }*/
        }
//        System.out.println("流程已经走完了....");
    }

    private static void buildStateMachine() {
        String machineId = "launch";
        StateMachineBuilder<LaunchStatus, LaunchEvent, LaunchContext> builder = StateMachineBuilderFactory.create();
        //external transition
        // 待提交 -> 待审核
        builder.externalTransition()
                .from(LaunchStatus.NOT_SUBMITTED)
                .to(LaunchStatus.NOT_APPROVED)
                .on(LaunchEvent.SUBMIT)
                .when(checkCondition())
                .perform(doAction());

        // 待审核 -> 审核驳回
        builder.externalTransition()
                .from(LaunchStatus.NOT_APPROVED)
                .to(LaunchStatus.REJECT_APPROVED)
                .on(LaunchEvent.REJECT_APPROVED)
                .when(checkCondition())
                .perform(doAction());

        // 待审核 -> 待开始
        builder.externalTransition()
                .from(LaunchStatus.NOT_APPROVED)
                .to(LaunchStatus.NOT_STARTED)
                .on(LaunchEvent.NOT_STARTED)
                .when(checkCondition())
                .perform(doAction());

        // 待审核 -> 灰度中
        builder.externalTransition()
                .from(LaunchStatus.NOT_APPROVED)
                .to(LaunchStatus.GRAYSCALE)
                .on(LaunchEvent.OPEN_TEST)
                .when(checkCondition())
                .perform(doAction());

        // 待审核 -> 已全量
        builder.externalTransition()
                .from(LaunchStatus.NOT_APPROVED)
                .to(LaunchStatus.ALL_FLOW)
                .on(LaunchEvent.NOT_OPEN_TEST)
                .when(checkCondition())
                .perform(doAction());

        // 灰度中 -> 已全量
        builder.externalTransition()
                .from(LaunchStatus.GRAYSCALE)
                .to(LaunchStatus.ALL_FLOW)
                .on(LaunchEvent.ADJUST_FLOW)
                .when(checkCondition1())
                .perform(doAction());

        // 待开始，灰度中，已全量 -> 已下线
        builder.externalTransitions()
                .fromAmong(LaunchStatus.NOT_APPROVED, LaunchStatus.GRAYSCALE, LaunchStatus.ALL_FLOW)
                .to(LaunchStatus.OFFLINE)
                .on(LaunchEvent.OFFLINE)
                .when(checkCondition())
                .perform(doAction());

        builder.build(machineId);
    }

    private static Action<LaunchStatus, LaunchEvent, LaunchContext> doAction() {
        return (from, to, event, context) -> {
            System.out.println("action...");
            context.setStatus(to);
        };
    }

    private static Condition<LaunchContext> checkCondition() {
        return context -> {
            System.out.println("condition...");
            return true;
        };
    }

    private static Condition<LaunchContext> checkCondition1() {
        return context -> {
            System.out.println("condition1...");
            return context.getFlowPercent() == 100;
        };
    }

    private static Condition<LaunchContext> checkCondition2() {
        return context -> {
            System.out.println("condition2...");
            return true;
        };
    }

    private static Condition<LaunchContext> checkCondition3() {
        return context -> {
            System.out.println("condition3...");
            return true;
        };
    }

}
