package com.SpringDataBaseBak.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author chenming
 * @description
 * @create: 2022-02-11
 */
public abstract class AbstractQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }

    protected abstract void doExecute(JobExecutionContext context, String taskName) throws Exception;
}
