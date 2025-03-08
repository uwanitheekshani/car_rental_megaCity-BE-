package org.uwani.mega.megacity.service.impl;

import okhttp3.*;

import java.io.IOException;

public class SmsServiceImpl {

    private static final String API_BASE_URL = "https://51j3dz.api.infobip.com"; // Infobip API base URL
    private static final String API_KEY = "a717c3552f65e91cbcae612bf56f8cfc-fc056aed-b21a-4ee4-b61e-1eddf3f75c44"; // Replace with your API Key
    private static final String SENDER = "447491163443"; // Your sender number

    private OkHttpClient client;

    public SmsServiceImpl() {
        this.client = new OkHttpClient().newBuilder().build();
    }

    public void sendSms(String recipientPhoneNumber, String messageText) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");

        // Prepare the request body with the required JSON format
        String jsonBody = String.format("{\"messages\":[{\"destinations\":[{\"to\":\"%s\"}],\"from\":\"%s\",\"text\":\"%s\"}]}",
                recipientPhoneNumber, SENDER, messageText);

        RequestBody body = RequestBody.create(mediaType, jsonBody);

        // Prepare the request with the necessary headers and body
        Request request = new Request.Builder()
                .url(API_BASE_URL + "/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", "App " + API_KEY) // Add your API key here
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // Execute the request and get the response
        Response response = client.newCall(request).execute();

        // Handle the response
        if (response.isSuccessful()) {
            System.out.println("✅ SMS sent successfully!");
        } else {
            System.out.println("❌ Failed to send SMS. Response: " + response.body().string());
        }
    }
}
