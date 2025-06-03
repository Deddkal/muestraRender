package com.technova.shopverse.shopverse_api.batch;

import com.technova.shopverse.shopverse_api.batch.model.CategoryCsv;
import com.technova.shopverse.shopverse_api.batch.model.ProductCsv;
import com.technova.shopverse.shopverse_api.model.Category;
import com.technova.shopverse.shopverse_api.model.Product;
import com.technova.shopverse.shopverse_api.repositories.CategoryRepository;
import com.technova.shopverse.shopverse_api.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CategoryRepository catRepo;
    private final ProductRepository prodRepo;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager, CategoryRepository catRepo, ProductRepository prodRepo) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.catRepo = catRepo;
        this.prodRepo = prodRepo;
    }
    // ========== STEP DEFINITION ==========

    // ===== READERS =====
    //FlatFileItemReader = Archivo plano

    @Bean
    public FlatFileItemReader<CategoryCsv> categoryReader() {
        return new FlatFileItemReaderBuilder<CategoryCsv>()
                .name("categoryReader")
                .resource(new ClassPathResource("data/categories.csv"))
                .linesToSkip(1) // ← ignora encabezado del CSV
                .delimited()
                .names("name", "description")
                .targetType(CategoryCsv.class)
                .build();
    }

    @Bean
    public FlatFileItemReader<ProductCsv> productReader() {
        return new FlatFileItemReaderBuilder<ProductCsv>()
                .name("productReader")
                .resource(new ClassPathResource("data/products.csv"))
                .linesToSkip(1)
                .delimited()
                .names("name", "description", "price", "categoryId")
                .targetType(ProductCsv.class)
                .build();
    }
    // Uso en Controller
    public FlatFileItemReader<CategoryCsv> categoryExternalReader(MultipartFile file)
            throws IOException {
        return new FlatFileItemReaderBuilder<CategoryCsv>()
                .name("categoryReader")
                .resource(new InputStreamResource(file.getInputStream()))
                .linesToSkip(1) // ← ignora encabezado del CSV
                .delimited()
                .names("name", "description")
                .targetType(CategoryCsv.class)
                .build();
    }
    // Uso en Controller
    public FlatFileItemReader<ProductCsv> productExternalReader(MultipartFile file)
            throws IOException {
        return new FlatFileItemReaderBuilder<ProductCsv>()
                .name("productReader")
                .resource(new InputStreamResource(file.getInputStream()))
                .linesToSkip(1)
                .delimited()
                .names("name", "description", "price", "categoryId")
                .targetType(ProductCsv.class)
                .build();
    }

    // ===== PROCESORS =====
    // Cuando se tiene solo una instruccion, solo hace un lambda.
    // Cuando se tienen mas instrucciones, el lambda tiene llaves y un return adentro.
    @Bean
    public ItemProcessor<CategoryCsv, Category> categoryProcessor() {
        return csv -> new Category(csv.getName(), csv.getDescription());
    }

    @Bean
    public ItemProcessor<ProductCsv, Product> productProcessor() {
        return csv -> {
            Category category = catRepo.findById(csv.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada: " + csv.getCategoryId()));
            return new Product(csv.getName(), csv.getDescription(), csv.getPrice(), category);
        };
    }

    // ===== WRITERS =====
    @Bean
    public ItemWriter<Category> categoryWriter() {
        return catRepo::saveAll;
    }

    @Bean
    public ItemWriter<Product> productWriter() {
        return prodRepo::saveAll;
    }

    // ========== STEP BUILDING ==========
    // Dentro del StepBuilder, el Chunk se refiere al procesamiento paralelo (de 10 en 10 u otro numero)
    @Bean
    public Step importCategoriesStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCategoriesStep", jobRepository)
                .<CategoryCsv, Category>chunk(10, transactionManager)
                .reader(categoryReader())
                .processor(categoryProcessor())
                .writer(categoryWriter())
                .build();
    }

    @Bean
    public Step importProductsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importProductsStep", jobRepository)
                .<ProductCsv, Product>chunk(10, transactionManager)
                .reader(productReader())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }
    // Uso en Controller
    public Step importCategoriesExternalStep(MultipartFile file
            /*, ItemProcessor<CategoryCsv, Category> processor, ItemWriter<Category> writer*/)
            throws IOException {
        return new StepBuilder("importCategoriesExtStep", jobRepository)
                .<CategoryCsv, Category>chunk(10, transactionManager)
                .reader(categoryExternalReader(file))
                //.processor(processor)
                //.writer(writer)
                .processor(categoryProcessor())
                .writer(categoryWriter())
                .build();
    }
    // Uso en Controller
    public Step importProductsExternalStep(MultipartFile file
            /*, ItemProcessor<ProductCsv, Product> processor, ItemWriter<Product> writer*/)
            throws IOException {
        return new StepBuilder("importProductsStep", jobRepository)
                .<ProductCsv, Product>chunk(10, transactionManager)
                .reader(productExternalReader(file))
                //.processor(processor)
                //.writer(writer)
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

    // ========== JOB BUILDING ==========
    // Ejecucion de STEPs con .start y .next
    @Bean
    public Job importCatalogJob(JobRepository jobRepository, Step importCategoriesStep, Step importProductsStep) {
        return new JobBuilder("importCatalogJob", jobRepository)
                .start(importCategoriesStep)
                .next(importProductsStep)
                .build();
    }
    // Uso en Controller
    public Job importCategoriesExternalJob(Step step) {
        return new JobBuilder("importCategoriesExternalJob", jobRepository)
                .start(step)
                .build();
    }
    // Uso en Controller
    public Job importProductsExternalJob(Step step) {
        return new JobBuilder("importProductsExternalJob", jobRepository)
                .start(step)
                .build();
    }

}
