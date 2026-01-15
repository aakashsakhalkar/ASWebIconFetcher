package com.aakash.aswebiconfetcher;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ASWebIconFetcher {

    private static final String API_BASE_URL =
            "https://aakashfaviconapi.netlify.app/api/favicon";

    private final OkHttpClient client;

    public ASWebIconFetcher() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public interface WebIconCallback {
        void onSuccess(String iconUrl, String domain, String source);
        void onError(String errorMessage, String iconUrl, String domain);
    }

    public void fetch(String websiteUrl, WebIconCallback callback) {

        if (websiteUrl == null || websiteUrl.trim().isEmpty()) {
            callback.onError(
                    "Website URL is empty",
                    null,
                    ""
            );
            return;
        }

        String encodedUrl;
        try {
            encodedUrl = URLEncoder.encode(
                    websiteUrl.trim(),
                    "UTF-8"
            );
        } catch (Exception e) {
            encodedUrl = websiteUrl.trim();
        }

        String requestUrl = API_BASE_URL + "?url=" + encodedUrl;

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(
                    @NonNull Call call,
                    @NonNull IOException e
            ) {
                callback.onError(
                        e.getMessage(),
                        null,
                        websiteUrl
                );
            }

            @Override
            public void onResponse(
                    @NonNull Call call,
                    @NonNull Response response
            ) throws IOException {

                String body = response.body() != null
                        ? response.body().string()
                        : "";

                try {
                    JSONObject json = new JSONObject(body);

                    String icon = json.optString("favicon");
                    String domain = json.optString("domain");
                    String source = json.optString("source");
                    String error = json.optString("error");

                    if (response.isSuccessful()) {
                        callback.onSuccess(icon, domain, source);
                    } else {
                        callback.onError(error, icon, domain);
                    }

                } catch (JSONException e) {
                    callback.onError(
                            "Invalid JSON response",
                            null,
                            websiteUrl
                    );
                }
            }
        });
    }
}
