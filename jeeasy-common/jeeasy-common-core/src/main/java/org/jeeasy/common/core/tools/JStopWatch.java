package org.jeeasy.common.core.tools;

import org.springframework.util.StopWatch;

public class JStopWatch extends StopWatch {

    public JStopWatch(String id) {
        super(id);
    }

    /**
     * 创建一个JStopWatch对象，指定任务名称，并开始计时
     *
     * @param taskName 任务名称
     * @return JStopWatch对象
     */
    public static JStopWatch create(String taskName) {
        return create("", taskName);
    }

    /**
     * 创建一个JStopWatch对象并启动计时
     *
     * @param id       JStopWatch的ID
     * @param taskName 计时任务名称
     * @return JStopWatch对象
     */
    public static JStopWatch create(String id, String taskName) {
        JStopWatch stopWatch = new JStopWatch(id);
        stopWatch.start(taskName);
        return stopWatch;
    }

    public static JStopWatch create() {
        return create("");
    }

    /**
     * 开始一个新的任务。
     *
     * @param newTaskName 新任务的名称
     */
    public void startNew(String newTaskName) {
        this.stop();
        this.start(newTaskName);
    }

    /**
     * 启动一个新的实例。
     */
    public void startNew() {
        this.startNew("");
    }

    /**
     * 停止计时器并返回格式良好的字符串表示
     *
     * @return 格式良好的计时器时间字符串
     */
    public String stopAndPrettyPrint() {
        this.stop();
        return "\n" + this.prettyPrint();
    }
}
