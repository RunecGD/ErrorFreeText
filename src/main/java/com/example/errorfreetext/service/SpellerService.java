package com.example.errorfreetext.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SpellerService {

    @Value("${app.yandex-speller-url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final int MAX_CHARS = 10000;
    private static final int IGNORE_DIGITS = 2;
    private static final int IGNORE_URLS = 4;

    public String correctText(String text, String lang) {
        int options = calculateOptions(text);
        List<String> chunks = splitIntoChunks(text, MAX_CHARS);
        StringBuilder correctedFullText = new StringBuilder();

        for (String chunk : chunks) {
            correctedFullText.append(processChunk(chunk, lang, options));
        }

        return correctedFullText.toString();
    }

    private int calculateOptions(String text) {
        int options = 0;
        if (text.matches(".*\\d.*")) {
            options |= IGNORE_DIGITS;
        }
        if (Pattern.compile("https?://\\S+|www\\.\\S+").matcher(text).find()) {
            options |= IGNORE_URLS;
        }
        return options;
    }

    private List<String> splitIntoChunks(String text, int size) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += size) {
            chunks.add(text.substring(i, Math.min(text.length(), i + size)));
        }
        return chunks;
    }

    private String processChunk(String chunk, String lang, int options) {
        try {
            String url = UriComponentsBuilder.fromUriString(apiUrl)
                    .queryParam("text", chunk)
                    .queryParam("lang", lang.toLowerCase())
                    .queryParam("options", options)
                    .queryParam("format", "plain")
                    .build()
                    .toUriString();

            // Yandex checkTexts returns an array of arrays of corrections
            // Example structure: [[{"word":"err","s":["correct"],...}]]
            Object[][] response = restTemplate.postForObject(url, null, Object[][].class);

            if (response == null || response.length == 0 || response[0].length == 0) {
                return chunk;
            }

            return applyCorrections(chunk, response[0]);
        } catch (Exception e) {
            log.error("Error calling Yandex API", e);
            throw new RuntimeException("Yandex API failure: " + e.getMessage());
        }
    }

    private String applyCorrections(String original, Object[] corrections) {
        // Yandex returns errors with position. We iterate backwards to replace without breaking indices.
        StringBuilder sb = new StringBuilder(original);
        List<Map<String, Object>> sortedCorrections = new ArrayList<>();

        for (Object o : corrections) {
            sortedCorrections.add((Map<String, Object>) o);
        }

        sortedCorrections.sort((a, b) -> (Integer) b.get("pos") - (Integer) a.get("pos"));

        for (Map<String, Object> corr : sortedCorrections) {
            int pos = (Integer) corr.get("pos");
            int len = (Integer) corr.get("len");
            List<String> suggestions = (List<String>) corr.get("s");

            if (!suggestions.isEmpty()) {
                sb.replace(pos, pos + len, suggestions.get(0));
            }
        }
        return sb.toString();
    }
}
