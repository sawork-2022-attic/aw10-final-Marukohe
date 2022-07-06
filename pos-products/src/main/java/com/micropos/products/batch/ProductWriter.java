package com.micropos.products.batch;

import com.micropos.common.model.Product;
import com.micropos.products.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Slf4j
public class ProductWriter implements ItemWriter<Product>, StepExecutionListener {

    ProductRepository productRepository;

    public ProductWriter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public void write(List<? extends Product> list) throws Exception {
        for (Product product : list) {
            if (product.getPrice() > 0) {
                log.info(String.format("Add product %s", product.getId()));
                productRepository.save(product).block();
            }
        }
    }
}
