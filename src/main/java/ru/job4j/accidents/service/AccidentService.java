package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    public AccidentService(AccidentMem accidentMem,
                           AccidentTypeService accidentTypeService,
                           RuleService ruleService) {
        this.accidentMem = accidentMem;
        this.accidentTypeService = accidentTypeService;
        this.ruleService = ruleService;
        initializationInsert();
    }

    public Accident create(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        return accidentMem.create(accident);
    }

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Accident findById(int id) {
        Optional<Accident> optAccident = accidentMem.findById(id);
        if (optAccident.isEmpty()) {
            throw new NoSuchElementException(" The Accident is not found");
        }
        return optAccident.get();
    }

    public void delete(int id) {
        accidentMem.delete(id);
    }

    public void update(Accident accident, String[] ruleIds) {
        setInsideObjects(accident, ruleIds);
        accidentMem.update(accident);
    }

    private void setInsideObjects(Accident accident, String[] ruleIds) {
        accident.setType(accidentTypeService.findById(accident.getType().getId()));
        accident.setRules(Arrays.stream(ruleIds).map(id -> ruleService.findById(Integer.parseInt(id)))
                .collect(Collectors.toSet()));
    }

    private void initializationInsert() {
        Accident accident1 = new Accident();
        accident1.setName("accident1");
        accident1.setAddress("address1");
        accident1.setText("text1");
        accident1.setType(accidentTypeService.findById(1));
        String[] rules1 = {"1", "2"};
        Accident accident2 = new Accident();
        accident2.setName("accident2");
        accident2.setAddress("address2");
        accident2.setText("text2");
        accident2.setType(accidentTypeService.findById(2));
        String[] rules2 = {"2", "3"};
        Accident accident3 = new Accident();
        accident3.setName("accident3");
        accident3.setAddress("address3");
        accident3.setText("text3");
        accident3.setType(accidentTypeService.findById(3));
        String[] rules3 = {"1", "3"};
        create(accident1, rules1);
        create(accident2, rules2);
        create(accident3, rules3);
    }
}
