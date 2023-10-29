package com.example.identityservice.Repository;

import com.example.identityservice.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository  extends ReactiveCrudRepository<User,Integer> {
    Mono<User> findByUsernameOrEmail(String username, String email);
}
