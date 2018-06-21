package cn.xo68.boot.job;

import cn.xo68.boot.job.properties.JobProperties;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties({JobProperties.class})
@ConditionalOnProperty(prefix = "wuxie.job",value = "enabled",havingValue = "true", matchIfMissing=true)
public class JobAutoConfiguration {

    @Bean
    public JobFactory jobFactoryInstance(ApplicationContext applicationContext) {
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, JobProperties jobProperties) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //factory.setDataSource(dataSource);
        factory.setAutoStartup(jobProperties.isAutoStartup());
        factory.setStartupDelay(jobProperties.getStartupDelay());
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(jobProperties.isOverwriteExistingJobs());
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey(jobProperties.getApplicationContextSchedulerContextKey());
        return factory;
    }

    @Bean
    public Properties quartzProperties() {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        try {
            propertiesFactoryBean.afterPropertiesSet();
            return propertiesFactoryBean.getObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        //Configure Main Scheduler Properties
        prop.put("quartz.scheduler.instanceName", "WX-Scheduler");
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");

        //Configure ThreadPool
        prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");
        prop.put("org.quartz.threadPool.threadPriority", "5");

        //Configure JobStore
        prop.put("org.quartz.jobStore.misfireThreshold", "60000");
        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.useProperties", "false");
        prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        prop.put("org.quartz.jobStore.isClustered", "true");

        prop.put("org.quartz.jobStore.dataSource", "default");
        prop.put("org.quartz.dataSource.default.driver", "com.mysql.jdbc.Driver");
        prop.put("org.quartz.dataSource.default.URL", "jdbc:mysql://localhost:3306/wxjob?characterEncoding=utf-8&useSSL=false");
        prop.put("org.quartz.dataSource.default.user", "wyy");
        prop.put("org.quartz.dataSource.default.password", "123456");
        prop.put("org.quartz.dataSource.default.maxConnections", "5");


        return prop;

    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener quartzInitializerListener() {
        return new QuartzInitializerListener();
    }

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Autowired(required = false)
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean){
        Scheduler scheduler= schedulerFactoryBean.getScheduler();
        return scheduler;
    }
}
