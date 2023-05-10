package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentTypeService {
    private final AccidentTypeMem accidentTypeMem;

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
}
