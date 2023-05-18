package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    public static final String FIND_ALL = "FROM Accident";
    public static final String FIND_BY_ID =
            "FROM Accident as a JOIN FETCH a.rules WHERE a.id = :fId";
    private final CrudRepository crudRepository;

    public Accident create(Accident accident) {
        crudRepository.run(session -> session.persist(accident));
        return accident;
    }

    public List<Accident> findAll() {
        return crudRepository.query(FIND_ALL, Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                Accident.class);
    }

    public void update(Accident accident) {
        crudRepository.run(session -> session.update(accident));
    }

    public void delete(int id) {
        Accident accident = new Accident();
        accident.setId(id);
        crudRepository.run(session -> session.remove(accident));
    }
}
