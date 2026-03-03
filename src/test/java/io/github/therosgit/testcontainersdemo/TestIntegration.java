package io.github.therosgit.testcontainersdemo;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public abstract class TestIntegration {
    // Postgres Container Setup
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("testPostgres")
            .withUsername("testUsername")
            .withPassword("testPassword");

    // S3 Container Setup
    static LocalStackContainer localStackContainer = new LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
            .withServices("s3");


    // start containers
    static  {
        postgreSQLContainer.start();
        localStackContainer.start();
    }

    // Override properties
    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        // Postgres
        registry.add(
                "spring.datasource.url",
                () -> postgreSQLContainer.getJdbcUrl()
        );

        // S3
        registry.add(
                "spring.cloud.aws.s3.endpoint",
                () -> localStackContainer.getEndpoint().toString()
        );
    }

    @BeforeAll
    static void createBucket() {
        S3Client s3Client = S3Client
                .builder()
                .endpointOverride(localStackContainer.getEndpoint())
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("test", "test")
                        )
                )
                .region(Region.of(localStackContainer.getRegion()))
                .build();

        s3Client.createBucket(
                CreateBucketRequest.builder()
                        .bucket("test-bucket")
                        .build()
        );
    }
}
