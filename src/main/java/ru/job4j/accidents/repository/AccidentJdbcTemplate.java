package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate {
    private static final String ACCIDENT_INSERT = """
            INSERT INTO accidents (name, description, address, type_id) 
            VALUES (?, ?, ?, ?)
            """;
    private static final String RULE_INSERT =
            "INSERT INTO accidents_rules (accident_id, rule_id) VALUES (?, ?)";
    private static final String FIND_ALL = """
            SELECT a.id, a.name, a.description, a.address, a.type_id, 
            t.name as type_name FROM accidents as a JOIN types as t
            ON a.type_id = t.id
            """;
    public static final String SELECT_RULES = """
        SELECT ar.rule_id, r.name as rule_name FROM accidents_rules as ar
        JOIN rules as r ON ar.rule_id = r.id WHERE ar.accident_id = ?
        """;
    public static final String FIND_BY_ID = """
            SELECT a.id, a.name, a.description, a.address, a.type_id,
            t.name as type_name FROM accidents as a JOIN types as t
            ON a.type_id = t.id WHERE a.id = ?
            """;
    public static final String UPDATE = """
            UPDATE accidents SET name = ?, description = ?, address = ?, type_id = ?
            WHERE id = ?
            """;
    public static final String DELETE_RULES =
            "DELETE FROM accidents_rules WHERE accident_id = ?";
    public static final String DELETE = "DELETE FROM accidents WHERE id = ?";
    private final RowMapper<Accident> accidentRowMapper = (res, row) -> {
        Accident accident = new Accident();
        accident.setId(res.getInt("id"));
        accident.setName(res.getString("name"));
        accident.setText(res.getString("description"));
        accident.setAddress(res.getString("address"));
        AccidentType type = new AccidentType();
        type.setId(res.getInt("type_id"));
        type.setName(res.getString("type_name"));
        accident.setType(type);
        this.setRules(accident);
        return accident;
    };
    private final JdbcTemplate jdbc;

    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(ACCIDENT_INSERT,
                    new String[] {"id"});
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getType().getId());
            return ps;
        }, keyHolder);
        accident.setId(keyHolder.getKey().intValue());
        accident.getRules().forEach(rule -> {
            jdbc.update(RULE_INSERT, accident.getId(), rule.getId());
        });
        return accident;
    }

    private void setRules(Accident accident) {
        List<Rule> list = jdbc.query(SELECT_RULES,
            (res, row) -> {
                Rule rule = new Rule();
                rule.setId(res.getInt("rule_id"));
                rule.setName(res.getString("rule_name"));
                return rule;
                }, accident.getId());
        accident.setRules(new HashSet<>(list));
    }

    public List<Accident> findAll() {
        return jdbc.query(FIND_ALL, accidentRowMapper);
    }

    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(jdbc.queryForObject(FIND_BY_ID, accidentRowMapper, id));
    }

    public void update(Accident accident) {
        jdbc.update(UPDATE, accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        jdbc.update(DELETE_RULES, accident.getId());
        accident.getRules().forEach(rule -> {
            jdbc.update(RULE_INSERT, accident.getId(), rule.getId());
        });
    }

    public void delete(int id) {
        jdbc.update(DELETE, id);
    }
}
