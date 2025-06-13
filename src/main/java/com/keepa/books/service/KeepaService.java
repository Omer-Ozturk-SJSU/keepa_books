package com.keepa.books.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class KeepaService {
    private static final String KEEPA_API_BASE_URL = "https://api.keepa.com";
    private static final int BOOKS_CATEGORY_ID = 283155;
    private final OkHttpClient client;
    private final ObjectMapper objectMapper;
    private final String apiKey;

    public KeepaService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("KEEPA_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("KEEPA_API_KEY environment variable is not set");
        }
        System.out.println("API Key loaded successfully (first 4 chars): " + apiKey.substring(0, 4) + "...");
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void getAndSaveTopAsins() throws IOException {
        List<String> asins = getTopRankedAsins();
        if (!asins.isEmpty()) {
            saveAsinsToFile(asins);
        } else {
            System.out.println("No ASINs found. Skipping file creation.");
        }
    }

    private List<String> getTopRankedAsins() throws IOException {
        String url = String.format(
                "%s/bestsellers?key=%s&domain=1&category=%d",
                KEEPA_API_BASE_URL, apiKey, BOOKS_CATEGORY_ID);

        System.out.println("Making request to URL: " + url.replace(apiKey, "API_KEY_HIDDEN"));

        Request request = new Request.Builder().url(url).get().build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            System.out.println("Response message: " + response.message());

            if (!response.isSuccessful()) {
                throw new IOException("Failed to get bestsellers: " + response.code());
            }

            String json = response.body().string();
            JsonNode root = objectMapper.readTree(json);
            List<String> asins = new ArrayList<>();

            // Debug: Print all top-level fields
            System.out.println("\nTop-level fields in response:");
            root.fieldNames().forEachRemaining(field -> System.out.println("- " + field));

            // Get ASINs from the bestsellers list
            JsonNode bestsellersList = root.get("bestSellersList");
            if (bestsellersList != null) {
                System.out.println("\nFound bestSellersList");
                // The ASINs are in a string array format
                String asinString = bestsellersList.toString();
                // Remove the quotes, brackets, and metadata
                asinString = asinString.replaceAll("[\\[\\]\"]", "")
                        .replaceAll("domainId:\\d+", "")
                        .replaceAll("categoryId:\\d+", "")
                        .replaceAll("asinList:", "");

                // Split by comma and add to list
                String[] asinArray = asinString.split(",");
                for (String asin : asinArray) {
                    asin = asin.trim();
                    // Only add if it's a valid ASIN (alphanumeric)
                    if (!asin.isEmpty() && asin.matches("^[A-Z0-9]+$")) {
                        asins.add(asin);
                    }
                }

                // Print first 5 ASINs for verification
                System.out.println("\nFirst 5 ASINs found:");
                for (int i = 0; i < Math.min(5, asins.size()); i++) {
                    System.out.println("- " + asins.get(i));
                }
                System.out.println("\nTotal ASINs found: " + asins.size());
            } else {
                System.out.println("\nbestSellersList is null");
            }

            // Debug: Check token information
            JsonNode tokensLeft = root.get("tokensLeft");
            if (tokensLeft != null) {
                System.out.println("\nTokens left: " + tokensLeft.asInt());
            }

            System.out.println("\nRetrieved " + asins.size() + " ASINs from bestsellers.");
            return asins;
        }
    }

    private void saveAsinsToFile(List<String> asins) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filename = "asins_" + timestamp + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String asin : asins) {
                writer.write(asin);
                writer.newLine();
            }
        }

        System.out.println("Successfully saved " + asins.size() + " ASINs to " + filename);
    }
}