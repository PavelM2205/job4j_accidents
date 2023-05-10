package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.AccidentMem;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccidentService {
    private final AccidentMem accidentMem;

    public Accident create(Accident accident) {
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
        accidentMem.update(accident);
    }
}
