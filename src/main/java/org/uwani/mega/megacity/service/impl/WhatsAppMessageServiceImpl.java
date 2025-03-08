package org.uwani.mega.megacity.service.impl;


import okhttp3.*;
import org.uwani.mega.megacity.service.WhatsAppMessageService;

public class WhatsAppMessageServiceImpl implements WhatsAppMessageService {
    private static final String API_BASE_URL = "https://api.infobip.com";
    private static final String API_KEY = "a717c3552f65e91cbcae612bf56f8cfc-fc056aed-b21a-4ee4-b61e-1eddf3f75c44";
    private static final String FROM_NUMBER = "447860099299";  // Sender's WhatsApp number

    @Override
    public void sendRegistrationWhatsAppMessage(String toPhoneNumber, String username) {
        // Create the OkHttp client
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");

        // Request body to send a WhatsApp template message
        String messageBody = "{\"messages\":[{\"from\":\"" + FROM_NUMBER + "\",\"to\":\"" + toPhoneNumber + "\","
                + "\"messageId\":\"" + java.util.UUID.randomUUID().toString() + "\","
                + "\"content\":{\"templateName\":\"test_whatsapp_template_en\","
                + "\"templateData\":{\"body\":{\"placeholders\":[\"" + username + "\"]}},\"language\":\"en\"}}]}";

        RequestBody body = RequestBody.create(mediaType, messageBody);

        // Build the request
        Request request = new Request.Builder()
                .url("https://api.infobip.com/whatsapp/1/message/template")
                .method("POST", body)
                .addHeader("Authorization", API_KEY)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        // Execute the request and handle the response
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("✅ WhatsApp message sent to " + toPhoneNumber + "!");
            } else {
                System.err.println("❌ Failed to send WhatsApp message: " + response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
