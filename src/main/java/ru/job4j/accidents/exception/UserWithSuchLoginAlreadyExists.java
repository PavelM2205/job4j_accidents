package ru.job4j.accidents.exception;

public class UserWithSuchLoginAlreadyExists extends RuntimeException {
    public UserWithSuchLoginAlreadyExists(String message) {
        super(message);
    }
}
