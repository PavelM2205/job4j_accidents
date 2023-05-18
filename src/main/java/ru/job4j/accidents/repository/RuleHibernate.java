package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@Repository
@AllArgsConstructor
public class RuleHibernate {
    private static final String FIND_ALL = "FROM Rule";
    private static final String FIND_BY_ID = "FROM Rule WHERE id = :fId";
    private static final String FIND_BY_MULTIPLE_ID =
            "FROM Rule WHERE id IN (:fIds)";
    private final CrudRepository crudRepository;

    public Rule create(Rule rule) {
        crudRepository.run(session -> session.persist(rule));
        return rule;
    }

    public List<Rule> findAll() {
        return crudRepository.query(FIND_ALL, Rule.class);
    }

    public Optional<Rule> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id), Rule.class);
    }

    public void update(Rule rule) {
        crudRepository.run(session -> session.update(rule));
    }

    public void delete(int id) {
        Rule rule = new Rule();
        rule.setId(id);
        crudRepository.run(session -> session.remove(rule));
    }

    public Set<Rule> findByIds(String[] ids) {
        List<Object> args = new ArrayList<>();
        for (String id : ids) {
            args.add(Integer.valueOf(id));
        }
        return new HashSet<>(crudRepository.query(FIND_BY_MULTIPLE_ID,
                Map.of("fIds", args), Rule.class));
    }
}
