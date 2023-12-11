package org.jeeasy.common.core.tools;

import org.springframework.util.StopWatch;

import java.text.NumberFormat;

public class JStopWatch extends StopWatch {
    private boolean _keepTaskList;

    public JStopWatch(String id) {
        super(id);
        this._keepTaskList = true;
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

    @Override
    public void setKeepTaskList(boolean keepTaskList) {
        super.setKeepTaskList(keepTaskList);
        this._keepTaskList = keepTaskList;
    }

    /**
     * 停止计时器并返回格式良好的字符串表示
     *
     * @return 格式良好的计时器时间字符串
     */
    public String stopAndPrettyPrint() {
        this.stop();
        StringBuilder sb = new StringBuilder("StopWatch '" + this.getId() + "': running time = " + this.getTotalTimeNanos() / 1000000 + " ms");
        sb.append('\n');
        if (!this._keepTaskList) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------\n");
            sb.append("ms         %     Task name\n");
            sb.append("---------------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(9);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            TaskInfo[] var4 = this.getTaskInfo();
            int var5 = var4.length;

            for (TaskInfo task : var4) {
                sb.append(nf.format(task.getTimeNanos() / 1000000)).append("  ");
                sb.append(pf.format((double) task.getTimeNanos() / (double) this.getTotalTimeNanos())).append("  ");
                sb.append(task.getTaskName()).append('\n');
            }
        }

        return sb.toString();
    }
}
