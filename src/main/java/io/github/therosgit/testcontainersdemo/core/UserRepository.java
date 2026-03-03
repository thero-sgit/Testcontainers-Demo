package io.github.therosgit.testcontainersdemo.core;

import io.github.therosgit.testcontainersdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
