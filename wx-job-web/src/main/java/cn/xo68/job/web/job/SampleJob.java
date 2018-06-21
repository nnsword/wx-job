package cn.xo68.job.web.job;

import java.io.Serializable;


import cn.xo68.boot.job.QuartzJob;
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
//        Object obj = (QuartzJob)context.getMergedJobDataMap().get("quartzJob");
//        if(obj!=null && obj instanceof QuartzJob){
//			QuartzJob quartzJob= (QuartzJob) obj;
//			logger.info("大吉大利、今晚吃鸡,jobName:{},jobGroup: {}", quartzJob.getJobName(), quartzJob.getJobGroup());
//		}else{
//			logger.info("大吉大利、今晚吃鸡,jobName:{}", jobName);
//		}
        logger.info("大吉大利、今晚吃鸡,jobName:{}", jobName);
		//QuartzJob quartzJob

	}
}
