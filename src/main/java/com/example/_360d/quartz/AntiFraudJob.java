package com.example._360d.quartz;

import com.example._360d.service.AntiFraudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AntiFraudJob implements Job {

    private final AntiFraudService antiFraudService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {

        log.info("starting a job");
        antiFraudService.monitor();
    }
}
