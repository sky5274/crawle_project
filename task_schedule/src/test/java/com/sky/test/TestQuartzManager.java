package com.sky.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.sky.pub.util.ComPubUtil;
import com.sky.task.TaskApplication;
import com.sky.task.core.QuartzManager;
import com.sky.task.demo.SimpleJob;
import com.sky.task.entity.JobTaskEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=TaskApplication.class,webEnvironment=SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TestQuartzManager {
	@Resource
	private QuartzManager quartzManager;
	
	@Test
	public void testAddJob() throws SchedulerException, InterruptedException {
		JobTaskEntity job=new JobTaskEntity();
		job.setTaskId(ComPubUtil.getUuid());
		job.setTaskName("test");
		job.setGroupId("test");
		job.setScheduleModel((byte)0);
		job.setTargetClass(SimpleJob.class.getName());
		job.setJsonParams("{test:test}");
		job.setCronExpression("*/5 * * * * ?");
		quartzManager.addJob(job);
	}
}
