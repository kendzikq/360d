package com.example._360d.configuration;

import com.example._360d.quartz.AntiFraudJob;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;

@Configuration
public class QuartzConfiguration {

    @Bean
    public JobDetailFactoryBean jobDetail() {
        var jobDetailFactory = new JobDetailFactoryBean();
        jobDetailFactory.setJobClass(AntiFraudJob.class);
        jobDetailFactory.setDurability(true);
        return jobDetailFactory;
    }

    @Bean
    public CronTriggerFactoryBean trigger(JobDetail jobDetail) {
        var trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(jobDetail);
        trigger.setCronExpression("*/10 * * * * ? *");
        return trigger;
    }
}