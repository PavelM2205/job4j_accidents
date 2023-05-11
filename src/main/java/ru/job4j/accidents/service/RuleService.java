package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RuleService {
    private final RuleMem ruleMem;

    public RuleService(RuleMem ruleMem) {
        this.ruleMem = ruleMem;
        initializationInsert();
    }

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
