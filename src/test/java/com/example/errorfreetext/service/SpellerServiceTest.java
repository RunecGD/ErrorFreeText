package com.example.errorfreetext.service;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpellerServiceTest {

    private final SpellerService spellerService = new SpellerService();

    @Test
    void testCalculateOptions_WithDigits() {
        int options = ReflectionTestUtils.invokeMethod(spellerService, "calculateOptions", "Text 123");
        assertEquals(2, options & 2, "Should have IGNORE_DIGITS (2) flag");
    }

    @Test
    void testCalculateOptions_WithUrl() {
        int options = ReflectionTestUtils.invokeMethod(spellerService, "calculateOptions", "Visit https://google.com");
        assertEquals(4, options & 4, "Should have IGNORE_URLS (4) flag");
    }

    @Test
    void testCalculateOptions_Both() {
        int options = ReflectionTestUtils.invokeMethod(spellerService, "calculateOptions", "123 and www.site.com");
        assertEquals(6, options & 6, "Should have both flags (2 | 4 = 6)");
    }

    @Test
    void testSplitIntoChunks() {
        String text = "ABCDEFGHIJ"; // 10 chars
        List<String> chunks = ReflectionTestUtils.invokeMethod(spellerService, "splitIntoChunks", text, 3);

        assertEquals(4, chunks.size());
        assertEquals("ABC", chunks.get(0));
        assertEquals("DEF", chunks.get(1));
        assertEquals("GHI", chunks.get(2));
        assertEquals("J", chunks.get(3));
    }
}
