package cn.xo68.job.web.job;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleJob implements  Job,Serializable {

	private final static Logger logger = LoggerFactory.getLogger(SampleJob.class);

	private static final long serialVersionUID = 1L;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("大吉大利、今晚吃鸡");
	}
}
