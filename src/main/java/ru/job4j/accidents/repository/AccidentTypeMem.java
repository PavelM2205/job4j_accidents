package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem {
    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public AccidentType create(AccidentType type) {
        type.setId(id.incrementAndGet());
        types.put(type.getId(), type);
        return type;
    }

    public List<AccidentType> findAll() {
        return new ArrayList<>(types.values());
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

    public void update(AccidentType type) {
        types.computeIfPresent(type.getId(), (key, value) -> type);
    }
}
