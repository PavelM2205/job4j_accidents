package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate {
    private static final String FIND_ALL = "SELECT * FROM types";
    private static final String INSERT = "INSERT INTO types (name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT * FROM types WHERE id = ?";
    private static final String DELETE = "DELETE FROM types WHERE id = ?";

    private final RowMapper<AccidentType> typeMapper = (res, row) -> {
        AccidentType type = new AccidentType();
        type.setId(res.getInt("id"));
        type.setName(res.getString("name"));
        return type;
    };

    private final JdbcTemplate jdbc;

    public AccidentType create(AccidentType accidentType) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
            ps.setString(1, accidentType.getName());
            return ps;
        }, keyHolder);
        accidentType.setId(keyHolder.getKey().intValue());
        return accidentType;

    }

    public List<AccidentType> findAll() {
        return jdbc.query(FIND_ALL, typeMapper);

    }

    public Optional<AccidentType> findById(int id) {
        AccidentType result = jdbc.queryForObject(FIND_BY_ID, typeMapper, id);
        return result.getId() == 0 ? Optional.empty() : Optional.of(result);
    }

    public void delete(int id) {
        jdbc.update(DELETE, id);
    }
}
