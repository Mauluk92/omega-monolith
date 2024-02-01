package it.aleph.omegamonolith.controller.job;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/job")
public interface JobController {


    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    void startJob(@RequestParam(name="jobName") String jobName);
}
