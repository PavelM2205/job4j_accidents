package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository

public class RuleMem {
    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public RuleMem() {
        initializationInsert();
    }

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

    public Set<Rule> findByIds(String[] ids) {
        return Arrays.stream(ids).map(id -> this.findById(Integer.parseInt(id)).get())
                .collect(Collectors.toSet());
    }

    private void initializationInsert() {
        Rule rule1 = new Rule();
        rule1.setName("Статья. 1");
        Rule rule2 = new Rule();
        rule2.setName("Статья. 2");
        Rule rule3 = new Rule();
        rule3.setName("Статья. 3");
        create(rule1);
        create(rule2);
        create(rule3);
    }
}
