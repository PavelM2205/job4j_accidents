package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccidentTypeService {
    private final AccidentTypeMem accidentTypeMem;

    public AccidentTypeService(AccidentTypeMem accidentTypeMem) {
        this.accidentTypeMem = accidentTypeMem;
        initializationInsert();
    }

    public AccidentType create(AccidentType type) {
        return accidentTypeMem.create(type);
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public AccidentType findById(int id) {
        Optional<AccidentType> optAccidentType = accidentTypeMem.findById(id);
        if (optAccidentType.isEmpty()) {
            throw new NoSuchElementException("The AccidentType is not found");
        }
        return optAccidentType.get();
    }

    public void update(AccidentType type) {
        accidentTypeMem.update(type);
    }

    private void initializationInsert() {
        AccidentType type1 = new AccidentType();
        type1.setName("Две машины");
        AccidentType type2 = new AccidentType();
        type2.setName("Машина и человек");
        AccidentType type3 = new AccidentType();
        type3.setName("Машина и велосипед");
        create(type1);
        create(type2);
        create(type3);
    }
}
