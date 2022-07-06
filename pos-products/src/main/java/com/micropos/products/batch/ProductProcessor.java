package com.micropos.products.batch;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.micropos.common.model.Product;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<JsonNode, Product>, StepExecutionListener {

    @Override
    public void beforeStep(StepExecution stepExecution) {

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public Product process(JsonNode jsonNode) {
        if (!jsonNode.has("asin")
                || !jsonNode.has("title")
                || !jsonNode.has("price")
                || !jsonNode.has("imageURLHighRes")) {
            return null;
        }

        String asin = jsonNode.get("asin").textValue();
        String title = jsonNode.get("title").textValue();
        String priceString = jsonNode.get("price").textValue();

        double price = 0;
        if (!priceString.isEmpty()) {
            if(priceString.startsWith("$")) {
                priceString = priceString.substring(1);
            }
            try {
                price = Double.parseDouble(priceString);
            } catch (NumberFormatException e) {
                price = 0;
            }
        }

        JsonNode imageURLHighRes = jsonNode.get("imageURLHighRes");
        String img = null;
        if (imageURLHighRes.isArray() && imageURLHighRes.size() > 0) {
            img = imageURLHighRes.get(0).textValue();
        }

        return new Product(asin, title, price, img);
    }
}
