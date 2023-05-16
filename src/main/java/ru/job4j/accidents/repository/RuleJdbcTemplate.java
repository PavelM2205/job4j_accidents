package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate {
    private static final String INSERT = "INSERT INTO rules (name) VALUES (?)";
    private static final String FIND_ALL = "SELECT * FROM rules";
    private static final String FIND_BY_ID = "SELECT * FROM rules WHERE id = ?";
    private static final String DELETE = "DELETE FROM rules WHERE id = ?";
    private static final String FIND_BY_MULTIPLE_ID =
            "SELECT * FROM rules WHERE id IN (%s)";

    private final RowMapper<Rule> ruleMapper = (res, row) -> {
        Rule rule = new Rule();
        rule.setId(res.getInt("id"));
        rule.setName(res.getString("name"));
        return rule;
    };

    private final JdbcTemplate jdbc;

    public Rule create(Rule rule) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT, new String[] {"id"});
            ps.setString(1, rule.getName());
            return ps;
        }, keyHolder);
        rule.setId(keyHolder.getKey().intValue());
        return rule;
    }

    public List<Rule> findAll() {
        return jdbc.query(FIND_ALL, ruleMapper);
    }

    public Optional<Rule> findById(int id) {
        Rule result = jdbc.queryForObject(FIND_BY_ID, ruleMapper, id);
        return result.getId() == 0 ? Optional.empty() : Optional.of(result);
    }

    public Set<Rule> findByIds(String[] ruleIds) {
        String args = String.join(",", ruleIds);
        String sqlQuery = String.format(FIND_BY_MULTIPLE_ID, args);
        return new HashSet<>(jdbc.query(sqlQuery, ruleMapper));
    }

    public void delete(int id) {
        jdbc.update(DELETE, id);
    }
}
