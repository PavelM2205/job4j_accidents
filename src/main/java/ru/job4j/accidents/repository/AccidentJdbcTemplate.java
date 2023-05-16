package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.*;

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
            t.name as type_name, ar.rule_id, r.name as rule_name FROM accidents as a JOIN types as t
            ON a.type_id = t.id JOIN accidents_rules as ar ON a.id = ar.accident_id JOIN rules as r
            ON ar.rule_id = r.id 
            """;

    private static final String FIND_BY_ID = """
            SELECT a.id, a.name, a.description, a.address, a.type_id, 
            t.name as type_name, ar.rule_id, r.name as rule_name FROM accidents as a JOIN types as t
            ON a.type_id = t.id JOIN accidents_rules as ar ON a.id = ar.accident_id JOIN rules as r
            ON ar.rule_id = r.id WHERE a.id = ?
            """;

    private static final String UPDATE = """
            UPDATE accidents SET name = ?, description = ?, address = ?, type_id = ?
            WHERE id = ?
            """;

    private static final String DELETE_RULES =
            "DELETE FROM accidents_rules WHERE accident_id = ?";

    private static final String DELETE = "DELETE FROM accidents WHERE id = ?";

    private final ResultSetExtractor<List<Accident>> accidentExtractor = res -> {
        Map<Integer, Accident> accidents = new LinkedHashMap<>();
        while (res.next()) {
            accidents.computeIfPresent(res.getInt("id"),
                    (key, value) -> {
                try {
                    Rule rule = new Rule();
                    rule.setId(res.getInt("rule_id"));
                    rule.setName(res.getString("rule_name"));
                    value.getRules().add(rule);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                return value;
            });
            accidents.computeIfAbsent(res.getInt("id"),
                    key -> {
                Accident accident = new Accident();
                try {
                    accident.setId(res.getInt("id"));
                    accident.setName(res.getString("name"));
                    accident.setText(res.getString("description"));
                    accident.setAddress(res.getString("address"));
                    AccidentType type = new AccidentType();
                    type.setId(res.getInt("type_id"));
                    type.setName(res.getString("type_name"));
                    accident.setType(type);
                    Rule rule = new Rule();
                    rule.setId(res.getInt("rule_id"));
                    rule.setName(res.getString("rule_name"));
                    Set<Rule> rules = new HashSet<>();
                    rules.add(rule);
                    accident.setRules(rules);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
                return accident;
            });
        }
        return new ArrayList<>(accidents.values());
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

    public List<Accident> findAll() {
        return jdbc.query(FIND_ALL, accidentExtractor);
    }

    public Optional<Accident> findById(int id) {
        List<Accident> list = jdbc.query(FIND_BY_ID, accidentExtractor, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
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
