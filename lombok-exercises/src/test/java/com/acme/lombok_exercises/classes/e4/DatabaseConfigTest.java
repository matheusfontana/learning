package com.acme.lombok_exercises.classes.e4;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Create config using static factory with all parameters
 * Create config using builder with only required fields
 * Update existing config to change password using toBuilder
 */
class DatabaseConfigTest {

    @Test
    void shouldCreateConfigUsingStaticFactory() {
        final DatabaseConfig dbConfig = setUpMaxVerstappen();

        assertThat(dbConfig).isNotNull();
        assertThat(dbConfig.getHost()).contains("aws");
        assertThat(dbConfig.getPort()).isEqualTo(3306);
        assertThat(dbConfig.getDatabase()).isEqualTo("redbullracing");
        assertThat(dbConfig.getUsername()).isEqualTo("racinguser33");
        assertThat(dbConfig.getPassword()).isNotEmpty();
        assertThat(dbConfig.isSslEnabled()).isTrue();
        assertThat(dbConfig.getConnectionTimeout()).isEqualTo(15000);
    }

    @Test
    void shouldCreateConfigUsingBuilderOnlyRequiredFields() {
        final DatabaseConfig dbConfig = DatabaseConfig.builder()
                .database("racingbulls")
                .username("isaac_hadjar")
                .password("1@mtheb3stR00k1eOf2025season")
                .build();

        assertThat(dbConfig).isNotNull();
        assertThat(dbConfig.getHost()).doesNotContain("aws");
        assertThat(dbConfig.getHost()).doesNotContain("mysql");
        assertThat(dbConfig.getHost()).contains("localhost");
        assertThat(dbConfig.getPort()).isNotEqualTo(3306);
        assertThat(dbConfig.getPort()).isEqualTo(5432);
        assertThat(dbConfig.getDatabase()).isNotEqualTo("redbullracing");
        assertThat(dbConfig.getDatabase()).isEqualTo("racingbulls");
        assertThat(dbConfig.getUsername()).isNotEqualTo("racinguser33");
        assertThat(dbConfig.getUsername()).isEqualTo("isaac_hadjar");
        assertThat(dbConfig.getPassword()).isNotEmpty();
        assertThat(dbConfig.isSslEnabled()).isTrue();
        assertThat(dbConfig.getConnectionTimeout()).isNotEqualTo(15000);
        assertThat(dbConfig.getConnectionTimeout()).isEqualTo(30000);

    }

    @Test
    void shouldUpdateExistingConfigToChangePasswordUsingToBuilder() {
        final DatabaseConfig dbConfig = setUpMaxVerstappen();

        final DatabaseConfig updatedConfig = dbConfig.toBuilder()
                .password("Th@t1sS1mplyL0v3lyLOL")
                .build();

        assertThat(updatedConfig).isNotNull();
        assertThat(dbConfig.getPassword()).isNotEqualTo(updatedConfig.getPassword());
    }

    private DatabaseConfig setUpMaxVerstappen() {
        return DatabaseConfig.of("1as33.rds.mysql.us-east1.aws",
                3306,
                "redbullracing",
                "racinguser33",
                UUID.randomUUID().toString(),
                true,
                15000);
    }
}