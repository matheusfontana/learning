package com.acme.lombok_exercises.classes.e4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Tasks:
 * Use @Builder.Default for reasonable defaults:
 * - host=“localhost”
 * - port=5432
 * - sslEnabled=true
 * - connectionTimeout=30000
 */
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Getter
public class DatabaseConfig {
    @Builder.Default
    private final String host = "localhost";
    @Builder.Default
    private final int port = 5432;
    private final String database;
    private final String username;
    private String password;
    @Builder.Default
    private boolean sslEnabled = true;
    @Builder.Default
    private int connectionTimeout = 30000;
}
