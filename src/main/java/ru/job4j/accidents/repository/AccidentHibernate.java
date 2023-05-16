package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHibernate {
    public static final String FIND_ALL = "FROM Accident";
    public static final String FIND_BY_ID =
            "FROM Accident as a JOIN FETCH a.rules WHERE a.id = :fId";
    private final SessionFactory sf;

    public Accident create(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.persist(accident);
            session.getTransaction().commit();
            return accident;
        }
    }

    public List<Accident> findAll() {
        try (Session session = sf.openSession()) {
            return session.createQuery(FIND_ALL, Accident.class).list();
        }
    }

    public Optional<Accident> findById(int id) {
        try (Session session = sf.openSession()) {
            return Optional.ofNullable(session.createQuery(
                    FIND_BY_ID, Accident.class)
                    .setParameter("fId", id).getSingleResultOrNull());
        }
    }

    public void update(Accident accident) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(accident);
            session.getTransaction().commit();
        }
    }

    public void delete(int id) {
        try (Session session = sf.openSession()) {
            Accident accident = new Accident();
            accident.setId(id);
            session.beginTransaction();
            session.remove(accident);
            session.getTransaction().commit();
        }
    }
}
