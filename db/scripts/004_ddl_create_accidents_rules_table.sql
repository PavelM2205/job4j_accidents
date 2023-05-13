CREATE TABLE accidents_rules (
    id SERIAL PRIMARY KEY,
    accident_id INT REFERENCES accidents(id) ON DELETE CASCADE,
    rule_id INT REFERENCES rules(id),
    UNIQUE (accident_id, rule_id)
);