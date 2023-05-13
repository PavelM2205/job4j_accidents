CREATE TABLE accidents (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    address TEXT,
    type_id INT REFERENCES types(id)
);