package ru.job4j.accidents.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomizedRuleRepositoryImpl implements CustomizedRuleRepository {
    private static final String FIND_BY_MULTIPLE_ID = "FROM Rule WHERE id IN (?1)";

    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Rule> findByIds(String[] ids) {
        List<Integer> args = new ArrayList<>();
        for (String id : ids) {
            args.add(Integer.valueOf(id));
        }
        return new HashSet<>(em.createQuery(FIND_BY_MULTIPLE_ID, Rule.class)
                .setParameter(1, args)
                .getResultList());
    }
}
