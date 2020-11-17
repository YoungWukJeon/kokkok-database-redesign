package me.kani.utility;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class UrlUtility {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(1))
            .build();

    public static boolean isAccessibleUrl(String url) {
        try {
            final var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            final var status = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .get(3000, TimeUnit.MILLISECONDS);

            return status == 200 || status == 301 || status == 302;
        } catch (Exception exception) {
            return false;
        }
    }
}
