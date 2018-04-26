package remote.jmx.taskMonitor.mbean;

/**
 * 对外提供线程池的工作状态
 *
 */
public interface ThreadPoolStatusMBean {
    public int getActiveThreads();
    public int getActiveTasks();
    public int getTotalTasks();
    public int getQueuedTasks();
    public double getAverageTaskTime();
    public String[] getActiveTaskNames();
    public String[] getQueuedTaskNames();
    public String getActiveTaskNamesDetail();
    public String getQueuedTaskNamesDetail();
}