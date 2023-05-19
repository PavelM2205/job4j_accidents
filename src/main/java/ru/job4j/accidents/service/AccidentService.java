package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentRepository accidentRepository;
    private final AccidentTypeRepository typeRepository;
    private final RuleRepository ruleRepository;

    public Accident create(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        return accidentRepository.save(accident);
    }

    public List<Accident> findAll() {
        return accidentRepository.findAll();
    }

    public Accident findById(int id) {
        return accidentRepository.findById(id).get();
    }

    public void update(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        accidentRepository.save(accident);
    }

    public void delete(int id) {
        Accident accident = new Accident();
        accident.setId(id);
        accidentRepository.delete(accident);
    }

    private void setInsideObjects(Accident accident, String[] ruleIds) {
        accident.setType(typeRepository.findById(accident.getType().getId()).get());
        List<Integer> args = new ArrayList<>();
        for (String id : ruleIds) {
            args.add(Integer.valueOf(id));
        }
        accident.setRules(ruleRepository.findRulesByIdIn(args));
    }
}
