package com.SpringDataBaseBak.utils;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author chenming
 * @description
 * @create: 2022-02-09
 */
@Slf4j
@Component
public class QuartzJobFactory extends AbstractQuartzJob {

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    protected void doExecute(JobExecutionContext context, String taskName) throws Exception {

        String beanName = StringUtils.substringBefore(taskName, "(");
        String bean = StringUtils.substringBeforeLast(beanName, ".");

        String methodName = StringUtils.substringBefore(taskName, "(");
        String method = StringUtils.substringAfterLast(methodName, ".");

        Object classTask = beanFactory.getBean(bean);
        Method methodTask = classTask.getClass().getDeclaredMethod(method);
        methodTask.invoke(classTask);

    }
}
