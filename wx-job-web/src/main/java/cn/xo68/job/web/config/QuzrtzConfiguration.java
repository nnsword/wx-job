/**
 * Copyright (c) 2018, 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何渠道使用、修改源代码.
 * 项目名称 : wxjobweb
 *
 * @version V1.0
 */
package cn.xo68.job.web.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 *  Quartz配置
 * @author admin
 * @date 2018/6/11 21:37
 *
 */
@Configuration
public class QuzrtzConfiguration {

    @Autowired(required = false)
    private DataSource dataSource;

    @Bean
    public JobFactory jobFactoryInstance(ApplicationContext applicationContext) {
        QuartzJobFactory jobFactory = new QuartzJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //factory.setDataSource(dataSource);
        factory.setAutoStartup(true);
        factory.setStartupDelay(5);//延时5秒启动
        factory.setQuartzProperties(quartzProperties());
        factory.setOverwriteExistingJobs(false);
        factory.setJobFactory(jobFactory);
        factory.setApplicationContextSchedulerContextKey("applicationContextSchedulerContextKey");
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
    public QuartzInitializerListener executorListener() {
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
