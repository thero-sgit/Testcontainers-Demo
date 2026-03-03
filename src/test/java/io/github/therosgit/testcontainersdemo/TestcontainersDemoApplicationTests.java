package io.github.therosgit.testcontainersdemo;

import io.github.therosgit.testcontainersdemo.core.UploadController;
import io.github.therosgit.testcontainersdemo.core.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import static org.assertj.core.api.Assertions.assertThat;

class TestcontainersDemoApplicationTests extends TestIntegration {
    @Autowired
    UploadController controller;

    @Autowired
    UserRepository repo;

    @Autowired
    S3Client s3;

    @Test
    void uploadShouldPersistUserAndStoreFile() {

        controller.upload();

        assertThat(repo.count()).isEqualTo(1);

        var response = s3.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket("demo-bucket")
                        .key("hello.txt")
                        .build());

        assertThat(
                response.asUtf8String()
        ).contains("real integration");
    }
}
