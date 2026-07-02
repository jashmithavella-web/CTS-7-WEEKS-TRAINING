package com.example.jpaexample.runner;

import com.example.jpaexample.model.User;
import com.example.jpaexample.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(new User("alice", "alice@example.com"));
        userRepository.save(new User("bob", "bob@example.com"));

        System.out.println("Saved users:");
        userRepository.findAll().forEach(System.out::println);
    }
}
