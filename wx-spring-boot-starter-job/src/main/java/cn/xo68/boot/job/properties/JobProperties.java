package cn.xo68.boot.job.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 分布式调度任务配置
 * @author wuxie
 * @date 2018-6-21
 */
@ConfigurationProperties("wuxie.job")
public class JobProperties {

    private boolean enabled;

    /**
     * 自动启动
     */
    private boolean autoStartup=true;
    /**
     * 启动延时的秒数
     */
    private int startupDelay=10;
    /**
     * 是否覆盖存在的任务
     */
    private boolean overwriteExistingJobs=false;

    private String applicationContextSchedulerContextKey="applicationContext";

    /**
     * 配置文件位置
     */
    private String quartzConfigLocation="classpath:quartz.properties";


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAutoStartup() {
        return autoStartup;
    }

    public void setAutoStartup(boolean autoStartup) {
        this.autoStartup = autoStartup;
    }

    public int getStartupDelay() {
        return startupDelay;
    }

    public void setStartupDelay(int startupDelay) {
        this.startupDelay = startupDelay;
    }

    public boolean isOverwriteExistingJobs() {
        return overwriteExistingJobs;
    }

    public void setOverwriteExistingJobs(boolean overwriteExistingJobs) {
        this.overwriteExistingJobs = overwriteExistingJobs;
    }

    public String getApplicationContextSchedulerContextKey() {
        return applicationContextSchedulerContextKey;
    }

    public void setApplicationContextSchedulerContextKey(String applicationContextSchedulerContextKey) {
        this.applicationContextSchedulerContextKey = applicationContextSchedulerContextKey;
    }

    public String getQuartzConfigLocation() {
        return quartzConfigLocation;
    }

    public void setQuartzConfigLocation(String quartzConfigLocation) {
        this.quartzConfigLocation = quartzConfigLocation;
    }
}
