package com.odeyalo.netflix.emailsenderservice.service.html.support;

import com.odeyalo.netflix.emailsenderservice.exceptions.FileReadingFailedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleCachedFileReader implements CachedFileReader {
    /**
     * Map that contains cache
     * Key - path to file
     * Value - file bytes
     */
    private final Map<String, byte[]> cache = new HashMap<>();

    @Override
    public byte[] readAllBytes(Path path) {
        try {
            byte[] bytes = Files.readAllBytes(path);
            this.cache.put(path.toAbsolutePath().toString(), bytes);
            return bytes;
        } catch (IOException e) {
            throw new FileReadingFailedException("File reading was failed", "We cannot process it right now. Please, try again later", e);
        }
    }

    @Override
    public boolean isAlreadyCached(Path path) {
        return cache.containsKey(path.toAbsolutePath().toString());
    }
}
