package cn.xo68.job.web.job;

import java.io.Serializable;

import cn.xo68.job.web.entity.QuartzJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleJob implements  Job,Serializable {

	private final static Logger logger = LoggerFactory.getLogger(SampleJob.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    String jobName= (String) context.getMergedJobDataMap().get("jobName");
        //QuartzJob quartzJob = (QuartzJob)context.getMergedJobDataMap().get("quartzJob");
		logger.info("大吉大利、今晚吃鸡,jobName:{},jobGroup: {}", jobName);
	}
}
