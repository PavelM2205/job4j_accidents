package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentJdbcTemplate;
import ru.job4j.accidents.repository.AccidentTypeJdbcTemplate;
import ru.job4j.accidents.repository.RuleJdbcTemplate;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentJdbcTemplate accidentJdbc;
    private final AccidentTypeJdbcTemplate typeJdbc;
    private final RuleJdbcTemplate ruleJdbc;

    public Accident create(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        return accidentJdbc.create(accident);
    }

    public List<Accident> findAll() {
        return accidentJdbc.findAll();
    }

    public Accident findById(int id) {
        Optional<Accident> optAccident = accidentJdbc.findById(id);
        if (optAccident.isEmpty()) {
            throw new NoSuchElementException(" The Accident is not found");
        }
        return optAccident.get();
    }

    public void delete(int id) {
        accidentJdbc.delete(id);
    }

    public void update(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        accidentJdbc.update(accident);
    }

    private void setInsideObjects(Accident accident, String[] ruleIds) {
        accident.setType(typeJdbc.findById(accident.getType().getId()).get());
        accident.setRules(ruleJdbc.findByIds(ruleIds));
    }
}
