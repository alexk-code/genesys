package com.github.alexk.jsonplaceholder.apis;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.alexk.jsonplaceholder.models.User;

public class JsonPlaceholderUserService {
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    public JsonPlaceholderUserService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<User> getUsers() throws IOException {
        try (Response response = getUsersResponse()) {
            if (!response.isSuccessful()) {
                throw new IllegalStateException("HTTP request failed with code: " + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new IllegalStateException("Response body is null");
            }
            String json = responseBody.string();
            return objectMapper.readValue(json, new TypeReference<>() {});
        }
    }

    public List<String> getUserNames() throws IOException {
        return getUsers().stream()
                .map(User::getName)
                .collect(Collectors.toList());
    }

    public List<String> getUserEmails() throws IOException {
        return getUsers().stream()
                .map(User::getEmail)
                .collect(Collectors.toList());
    }

    private Response getUsersResponse() throws IOException {
        String path = "/users";
        Request request = new Request.Builder()
                .url(baseUrl + path)
                .build();
        return client.newCall(request).execute();
    }
}
