package io.github.therosgit.testcontainersdemo.core;

import io.github.therosgit.testcontainersdemo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UploadController {
    private final StorageService storage;
    private final UserRepository repo;

    @PostMapping("/upload")
    public String upload() {

        repo.save(new User(null, "demo@test.com"));

        storage.upload(
                "hello.txt",
                "real integration test".getBytes()
        );

        return "ok";
    }
}