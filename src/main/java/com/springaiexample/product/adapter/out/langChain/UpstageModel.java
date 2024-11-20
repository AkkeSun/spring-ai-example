package com.springaiexample.product.adapter.out.langChain;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class UpstageModel {

    private final RestClient restClient;

    private final Map<String, Object> requestBody;

    public UpstageModel () {
        this.restClient = RestClient.builder()
            .baseUrl("https://api.upstage.ai")
            .defaultHeader("Authorization", "Bearer up_EryelVqUJjNV6mj8g8F6baAp76IqG")
            .build();
        this.requestBody = new HashMap<>();
        requestBody.put("model", "solar-pro");
        requestBody.put("stream", false);
    }

    public String generate(String prompt) {
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        requestBody.put("messages", List.of(message));

        String response = restClient.post()
            .uri("/v1/solar/chat/completions")
            .contentType(MediaType.APPLICATION_JSON)
            .body(requestBody)
            .retrieve()
            .body(String.class);

        JsonObject json = new Gson().fromJson(response, JsonObject.class);
        return json.getAsJsonArray("choices").get(0).getAsJsonObject()
            .getAsJsonObject("message")
            .get("content").getAsString();
    }
}
