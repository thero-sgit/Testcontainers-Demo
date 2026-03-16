package io.github.therosgit.testcontainersdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;

@ImportTestcontainers(TestIntegration.class)
public class TestTestcontainersDemoApplication extends TestIntegration {

    public static void main(String[] args) {
        SpringApplication
                .from(TestcontainersDemoApplication::main)
                .with(TestTestcontainersDemoApplication.class)
                .run(args);
    }

}
