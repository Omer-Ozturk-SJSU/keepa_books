package com.keepa.books.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.keepa.books.dto.KeepaProduct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.text.NumberFormat;
import java.util.Locale;

public class KeepaProductService {
    private static final Logger logger = LoggerFactory.getLogger(KeepaProductService.class);
    private static final String KEEPA_API_BASE_URL = "https://api.keepa.com/product";
    private static final int DOMAIN_ID = 1; // Amazon.com
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public KeepaProductService(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
    }

    public KeepaProduct getProductByAsin(String asin) throws IOException {
        String url = buildProductUrl(asin);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        int retryCount = 0;
        while (retryCount < MAX_RETRIES) {
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "No error body";
                    logger.error("API call failed for ASIN {}: {} - {}", asin, response.code(), errorBody);

                    if (response.code() == 429) { // Rate limit
                        retryCount++;
                        if (retryCount < MAX_RETRIES) {
                            Thread.sleep(RETRY_DELAY_MS * retryCount);
                            continue;
                        }
                    }
                    throw new IOException("API call failed: " + response.code());
                }

                String responseBody = response.body().string();
                JsonNode rootNode = objectMapper.readTree(responseBody);

                // Log token usage
                if (rootNode.has("tokensLeft")) {
                    logger.info("Tokens left: {}", rootNode.get("tokensLeft").asInt());
                }
                if (rootNode.has("tokensConsumed")) {
                    logger.info("Tokens consumed: {}", rootNode.get("tokensConsumed").asInt());
                }

                if (rootNode.has("products") && rootNode.get("products").isArray()
                        && rootNode.get("products").size() > 0) {
                    return objectMapper.treeToValue(rootNode.get("products").get(0), KeepaProduct.class);
                } else {
                    logger.error("No product data found in response for ASIN: {}", asin);
                    return null;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Request interrupted", e);
            }
        }
        throw new IOException("Max retries exceeded for ASIN: " + asin);
    }

    public List<KeepaProduct> processAsinsFromFile(String filePath, int limit) throws IOException {
        List<KeepaProduct> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String asin = reader.readLine(); // Read only the first line
            if (asin != null) {
                asin = asin.trim();
                if (!asin.isEmpty()) {
                    try {
                        logger.info("Processing ASIN: {}", asin);
                        KeepaProduct product = getProductByAsin(asin);
                        if (product != null) {
                            products.add(product);
                            logger.info("Successfully processed ASIN: {}", asin);
                        }
                    } catch (Exception e) {
                        logger.error("Error processing ASIN {}: {}", asin, e.getMessage());
                    }
                }
            }
        }

        return products;
    }

    private String buildProductUrl(String asin) {
        return String.format(
                "%s?key=%s&domain=%d&asin=%s&stats=180&update=48&history=1&days=90&rating=1&buybox=1&stock=1",
                KEEPA_API_BASE_URL,
                apiKey,
                DOMAIN_ID,
                asin);
    }

    private String formatPrice(JsonNode priceNode) {
        if (priceNode == null)
            return "N/A";
        try {
            int price;
            if (priceNode.isArray()) {
                // For arrays, find the first non-zero value
                for (JsonNode node : priceNode) {
                    price = node.asInt();
                    if (price > 0) {
                        return currencyFormat.format(price / 100.0);
                    }
                }
                return "N/A";
            } else {
                price = priceNode.asInt();
                return price > 0 ? currencyFormat.format(price / 100.0) : "N/A";
            }
        } catch (Exception e) {
            logger.debug("Error formatting price: {}", e.getMessage());
            return "N/A";
        }
    }

    private String formatStock(JsonNode stockNode) {
        if (stockNode == null)
            return "N/A";
        try {
            int stock;
            if (stockNode.isArray()) {
                // For arrays, find the first non-negative value
                for (JsonNode node : stockNode) {
                    stock = node.asInt();
                    if (stock >= 0) {
                        return String.valueOf(stock);
                    }
                }
                return "N/A";
            } else {
                stock = stockNode.asInt();
                return stock >= 0 ? String.valueOf(stock) : "N/A";
            }
        } catch (Exception e) {
            logger.debug("Error formatting stock: {}", e.getMessage());
            return "N/A";
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java KeepaProductService <apiKey> <asinFilePath>");
            return;
        }

        String apiKey = args[0];
        String asinFilePath = args[1];

        try {
            KeepaProductService service = new KeepaProductService(apiKey);
            List<KeepaProduct> products = service.processAsinsFromFile(asinFilePath, 1);

            if (!products.isEmpty()) {
                KeepaProduct product = products.get(0);
                System.out.println("\nProduct Details:");
                System.out.println("ASIN: " + product.getAsin());
                System.out.println("Title: " + product.getTitle());
                System.out.println("Brand: " + product.getBrand());
                System.out.println("Product Type: " + product.getProductType());

                if (product.getStats() != null) {
                    System.out.println("\nPricing Information:");
                    // Log raw values for debugging
                    logger.debug("Raw current price: {}", product.getStats().getCurrent());
                    logger.debug("Raw min price: {}", product.getStats().getMin());
                    logger.debug("Raw max price: {}", product.getStats().getMax());

                    System.out.println("Current Price: " + service.formatPrice(product.getStats().getCurrent()));
                    System.out.println("Average Price: " + service.formatPrice(product.getStats().getAvg()));
                    System.out.println("Minimum Price: " + service.formatPrice(product.getStats().getMin()));
                    System.out.println("Maximum Price: " + service.formatPrice(product.getStats().getMax()));

                    System.out.println("\nBuy Box Information:");
                    System.out.println("Current Buy Box: " + service.formatPrice(product.getStats().getCurrent()));
                    System.out.println("90-Day Average: " + service.formatPrice(product.getStats().getAvg90()));
                    System.out.println("180-Day Average: " + service.formatPrice(product.getStats().getAvg180()));

                    System.out.println("\nStock Information:");
                    System.out.println("Current Stock: " + service.formatStock(product.getStats().getCurrent()));
                    System.out.println("Out of Stock Percentage: " +
                            (product.getStats().getOutOfStockPercentage() != null
                                    ? product.getStats().getOutOfStockPercentage().asInt() + "%"
                                    : "N/A"));
                    System.out.println("Out of Stock (30 days): " +
                            (product.getStats().getOutOfStockPercentage30() != null
                                    ? product.getStats().getOutOfStockPercentage30().asInt() + "%"
                                    : "N/A"));
                }
            } else {
                System.out.println("No product data was retrieved.");
            }
        } catch (Exception e) {
            logger.error("Error in main: ", e);
        }
    }
}