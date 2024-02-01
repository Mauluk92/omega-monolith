package it.aleph.omegamonolith.controller.job.impl;

import it.aleph.omegamonolith.controller.job.JobController;
import it.aleph.omegamonolith.service.job.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobControllerImpl implements JobController {

    private final JobService jobService;
    @Override
    public void startJob(String jobName) {
        jobService.startJob(jobName);
    }
}
