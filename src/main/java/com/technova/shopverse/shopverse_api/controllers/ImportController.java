package com.technova.shopverse.shopverse_api.controllers;

import com.technova.shopverse.shopverse_api.batch.BatchConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
public class ImportController {
    // API de Activacion para carga de Jobs
    private final JobLauncher jobLauncher;
    private final BatchConfig batchConfig;
    // Se quitan Processors/Writers y se referencian dentro de BatchConfig.java
    /*private final ItemProcessor<CategoryCsv, Category> categoryProcessor;
    private final ItemWriter<Category> categoryWriter;
    private final ItemProcessor<ProductCsv, Product> productProcessor;
    private final ItemWriter<Product> productWriter;*/

    public ImportController(JobLauncher jobLauncher, BatchConfig batchConfig
            /*, ItemProcessor<CategoryCsv, Category> categoryProcessor, ItemWriter<Category> categoryWriter,
            ItemProcessor<ProductCsv, Product> productProcessor, ItemWriter<Product> productWriter*/) {
        this.jobLauncher = jobLauncher;
        this.batchConfig = batchConfig;
        /*this.categoryProcessor = categoryProcessor;
        this.categoryWriter = categoryWriter;
        this.productProcessor = productProcessor;
        this.productWriter = productWriter;*/
    }

    @PostMapping("/categories")
    public ResponseEntity<String> importCategories(@RequestParam("file") MultipartFile file)
        throws Exception {
        Step step = batchConfig.importCategoriesExternalStep(file/*, categoryProcessor, categoryWriter*/);
        Job job = batchConfig.importCategoriesExternalJob(step);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        return ResponseEntity.ok("Importacion iniciada");
    }

    @PostMapping("/products")
    public ResponseEntity<String> importProducts(@RequestParam("file") MultipartFile file)
            throws Exception {
        Step step = batchConfig.importProductsExternalStep(file/*, productProcessor, productWriter*/);
        Job job = batchConfig.importProductsExternalJob(step);

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        return ResponseEntity.ok("Importacion iniciada");
    }
}
