package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccidentService {
    private final AccidentMem accidentMem;
    private final AccidentTypeService accidentTypeService;

    public AccidentService(AccidentMem accidentMem, AccidentTypeService accidentTypeService) {
        this.accidentMem = accidentMem;
        this.accidentTypeService = accidentTypeService;
        initializationInsert();
    }

    public Accident create(Accident accident) {
        setInsideObjects(accident);
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

    public void update(Accident accident) {
        setInsideObjects(accident);
        accidentMem.update(accident);
    }

    private void setInsideObjects(Accident accident) {
        if (accident.getType().getId() != 0 && accident.getType().getName() == null) {
            accident.setType(accidentTypeService.findById(accident.getType().getId()));
        }
    }

    private void initializationInsert() {
        Accident accident1 = new Accident();
        accident1.setName("accident1");
        accident1.setAddress("address1");
        accident1.setText("text1");
        accident1.setType(accidentTypeService.findById(1));
        Accident accident2 = new Accident();
        accident2.setName("accident2");
        accident2.setAddress("address2");
        accident2.setText("text2");
        accident2.setType(accidentTypeService.findById(2));
        Accident accident3 = new Accident();
        accident3.setName("accident3");
        accident3.setAddress("address3");
        accident3.setText("text3");
        accident3.setType(accidentTypeService.findById(3));
        create(accident1);
        create(accident2);
        create(accident3);
    }
}
