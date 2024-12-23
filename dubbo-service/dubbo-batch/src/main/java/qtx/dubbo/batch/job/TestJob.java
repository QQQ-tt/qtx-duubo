package qtx.dubbo.batch.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qtx
 * @since 2023/10/8 19:22
 */
@Slf4j
@Configuration
public class TestJob {

    private final JobRepository jobRepository;

    public TestJob(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean(name = "footballJob")
    public Job footballJob(@Qualifier("testStepRead") Step step) {
        return new JobBuilder("footballJob", jobRepository)
                .start(step)
                .build();
    }

}
