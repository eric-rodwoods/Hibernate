package jm.task.core.jdbc.dao;

public enum Queries {
    CREATE_TABLE("CREATE TABLE IF NOT EXISTS users (id BIGSERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, lastName VARCHAR(255) NOT NULL, age SMALLINT NOT NULL);"),
    DROP_TABLE("DROP TABLE IF EXISTS users;"),
    SAVE("INSERT INTO users (id, name, lastName, age) VALUES (DEFAULT, ?, ?, ?) RETURNING name;"),
    REMOVE("DELETE FROM users WHERE id = ?;"),
    GET_ALL("SELECT * from users;"),
    CLEAN_TABLE("DELETE FROM users;");

    public String QUERY;
    Queries(String QUERY) { this.QUERY = QUERY; }
}