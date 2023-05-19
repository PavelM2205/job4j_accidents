package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface RuleRepository extends CrudRepository<Rule, Integer> {

    Set<Rule> findRulesByIdIn(List<Integer> args);

    default Set<Rule> findRulesByIdIn(String[] ruleIds) {
        List<Integer> args = new ArrayList<>();
        for (String id : ruleIds) {
            args.add(Integer.parseInt(id));
        }
        return findRulesByIdIn(args);
    }
}
