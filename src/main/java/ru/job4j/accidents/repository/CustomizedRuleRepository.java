package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface CustomizedRuleRepository {

    Set<Rule> findByIds(String[] ids);
}
