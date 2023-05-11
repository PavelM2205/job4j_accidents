package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.*;

@Service
@AllArgsConstructor
public class RuleService {
    private final RuleMem ruleMem;

    public Rule create(Rule rule) {
        return ruleMem.create(rule);
    }

    public List<Rule> findAll() {
        return ruleMem.findAll();
    }

    public Rule findById(int id) {
        Optional<Rule> optRule = ruleMem.findById(id);
        if (optRule.isEmpty()) {
            throw new NoSuchElementException("Rule is not found");
        }
        return optRule.get();
    }

    public Set<Rule> findByIds(String[] ids) {
        return ruleMem.findByIds(ids);
    }
}
