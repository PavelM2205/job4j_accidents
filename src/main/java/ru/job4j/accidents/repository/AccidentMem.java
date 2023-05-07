package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {
    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public AccidentMem() {
        initializationInsert();
    }

    public Accident create(Accident accident) {
        accident.setId(id.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    public void delete(int id) {
        accidents.remove(id);
    }

    private void initializationInsert() {
        Accident accident1 = new Accident();
        accident1.setName("accident1");
        accident1.setAddress("address1");
        accident1.setText("text1");
        Accident accident2 = new Accident();
        accident2.setName("accident2");
        accident2.setAddress("address2");
        accident2.setText("text2");
        Accident accident3 = new Accident();
        accident3.setName("accident3");
        accident3.setAddress("address3");
        accident3.setText("text3");
        create(accident1);
        create(accident2);
        create(accident3);
    }
}
