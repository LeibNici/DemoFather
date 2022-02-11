package com.SpringDataBaseBak.utils;


import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author chenming
 */
public class ScheduleUtils {


    public static void createScheduleJob(Scheduler scheduler, String taskName, String schedule_cron) throws SchedulerException {

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(schedule_cron);
        cronScheduleBuilder = handleCronScheduleMisfirePolicy(cronScheduleBuilder);

        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(taskName, "DEFAULT_GROUP").withSchedule(cronScheduleBuilder).build();

        JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class).withIdentity(getJobKey("DataBase")).build();

        jobDetail.getJobDataMap().put("TASK_PROPERTIES",null);

        if (scheduler.checkExists(getJobKey("DataBase"))){
            scheduler.deleteJob(getJobKey("DataBase"));
        }

        scheduler.scheduleJob(jobDetail, cronTrigger);

    }

    private static JobKey getJobKey(String name) {
        return JobKey.jobKey(name);
    }


    private static CronScheduleBuilder handleCronScheduleMisfirePolicy(CronScheduleBuilder cronScheduleBuilder) {
        return  cronScheduleBuilder.withMisfireHandlingInstructionIgnoreMisfires();
    }
}
