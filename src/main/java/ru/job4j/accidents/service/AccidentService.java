package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentHibernate;
import ru.job4j.accidents.repository.AccidentTypeHibernate;
import ru.job4j.accidents.repository.RuleHibernate;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentHibernate accidentHibernate;
    private final AccidentTypeHibernate typeHibernate;
    private final RuleHibernate ruleHibernate;

    public Accident create(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        return accidentHibernate.create(accident);
    }

    public List<Accident> findAll() {
        return accidentHibernate.findAll();
    }

    public Accident findById(int id) {
        Optional<Accident> optAccident = accidentHibernate.findById(id);
        if (optAccident.isEmpty()) {
            throw new NoSuchElementException(" The Accident is not found");
        }
        return optAccident.get();
    }

    public void delete(int id) {
        accidentHibernate.delete(id);
    }

    public void update(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        accidentHibernate.update(accident);
    }

    private void setInsideObjects(Accident accident, String[] ruleIds) {
        accident.setType(typeHibernate.findById(accident.getType().getId()).get());
        accident.setRules(ruleHibernate.findByIds(ruleIds));
    }
}
