package cn.xo68.job.web.config;

import cn.xo68.job.web.entity.QuartzJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * 初始化一个测试Demo任务
 * 创建者 科帮网
 * 创建时间	2018年4月3日
 */
@Component
public class TaskRunner implements ApplicationRunner{
    
	private final static Logger LOGGER = LoggerFactory.getLogger(TaskRunner.class);
	

	@Autowired
    private Scheduler scheduler;
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public void run(ApplicationArguments var) throws Exception{

		LOGGER.info("初始化测试任务");
		QuartzJob quartzJob = new QuartzJob();
		quartzJob.setJobName("test01");
		quartzJob.setJobGroup("test");
		quartzJob.setDescription("测试任务");
		quartzJob.setJobClassName("cn.xo68.job.web.job.SampleJob");
		quartzJob.setCronExpression("0/20 * * * * ?");
		Class cls = Class.forName(quartzJob.getJobClassName()) ;
		cls.newInstance();

        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

        // 不存在，创建一个
        if (trigger == null) {
            JobDetail jobDetail = JobBuilder.newJob(cls)
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .usingJobData("jobName", quartzJob.getJobName())
                    .withDescription(quartzJob.getDescription()).build();
            jobDetail.getJobDataMap().put("quartzJob", quartzJob);

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());

            // 按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .startNow()
                    .withSchedule(scheduleBuilder).build();

            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzJob.getCronExpression());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
		//scheduler.e
    }

}