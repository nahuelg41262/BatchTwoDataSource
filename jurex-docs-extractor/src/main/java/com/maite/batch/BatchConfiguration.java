package com.maite.batch;

import com.maite.jurex.model.entities.TMPMaite;
import com.maite.jurex.model.services.ITMPMaiteService;
import com.maite.model.entities.Contrato;
import com.maite.model.services.IContratoService;
import com.maite.properties.ConverterProperties;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    ConverterProperties converterProperties;
    @Autowired
    private ITMPMaiteService tmpMaiteService;
    @Autowired
    private IContratoService contratoService;
    @Autowired
    private FileProcessor fileProcessor;

    @Override
    public void setDataSource(DataSource dataSource) {
        // override to do not set datasource even if a datasource exist.
        // initialize will use a Map based JobRepository (instead of database)
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ConcurrentTaskExecutor concurrentTaskExecutor = new ConcurrentTaskExecutor();

        return concurrentTaskExecutor;
    }

    @Bean("ocrJob")
    public Job ocrJob() {
        return jobBuilderFactory.get("ocrJob").incrementer(new RunIdIncrementer())
                .flow(step()).end().build();
    }

    protected Step step() {
        return stepBuilderFactory.get("stepMaiteExtractor").<List<TMPMaite>, List<Optional<Contrato>>>chunk(1).reader(fileReader())
                .processor(fileProcessor()).writer(fileWriter()).taskExecutor(taskExecutor())
                .throttleLimit(1).build();
    }

    public FileReader fileReader() {
        return new FileReader(tmpMaiteService, contratoService);
    }

    public FileProcessor fileProcessor() {

        return fileProcessor;
    }

    public FileWriter fileWriter() {
        return new FileWriter(contratoService);
    }

}
