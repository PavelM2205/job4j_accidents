package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository

public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public Rule create(Rule rule) {
        rule.setId(id.incrementAndGet());
        rules.put(rule.getId(), rule);
        return rule;
    }

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    public Optional<Rule> findById(int id) {
        return Optional.ofNullable(rules.get(id));
    }
}
