package io.github.therosgit.testcontainersdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
public class TestTestcontainersDemoApplication extends TestIntegration {

    public static void main(String[] args) {
        SpringApplication.from(TestcontainersDemoApplication::main).run(args);
    }

}
