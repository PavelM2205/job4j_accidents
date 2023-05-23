package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.exception.UserWithSuchLoginAlreadyExists;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;


@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        try {
            userRepository.save(user);
        } catch (Exception exc) {
            throw new UserWithSuchLoginAlreadyExists("User with such name already exists");
        }
        return user;
    }
}
