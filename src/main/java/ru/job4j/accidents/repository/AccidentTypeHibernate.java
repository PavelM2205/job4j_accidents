package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHibernate {
    private static final String FIND_ALL = "FROM AccidentType";
    private static final String FIND_BY_ID = "FROM AccidentType WHERE id = :fId";
    private final CrudRepository crudRepository;

    public AccidentType create(AccidentType accidentType) {
        crudRepository.run(session -> session.persist(accidentType));
        return accidentType;
    }

    public List<AccidentType> findAll() {
        return crudRepository.query(FIND_ALL, AccidentType.class);
    }

    public Optional<AccidentType> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, Map.of("fId", id),
                AccidentType.class);
    }

    public void update(AccidentType accidentType) {
        crudRepository.run(session -> session.update(accidentType));
    }

    public void delete(int id) {
        AccidentType accidentType = new AccidentType();
        accidentType.setId(id);
        crudRepository.run(session -> session.remove(accidentType));
    }
}
