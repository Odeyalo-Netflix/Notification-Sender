package com.odeyalo.netflix.emailsenderservice.service.html.support;

import java.nio.file.Path;

/**
 * Represents a file reader with cached function
 */
public interface CachedFileReader {

    byte[] readAllBytes(Path path);

    boolean isAlreadyCached(Path path);

    int clearOldCache();
}
