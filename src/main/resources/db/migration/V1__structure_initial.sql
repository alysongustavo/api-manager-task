CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS task_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS category (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('category_seq'),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS role (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('role_seq'),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS "user" (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('user_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    status BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS "user_role" (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS task (
    id BIGINT PRIMARY KEY DEFAULT NEXTVAL('task_seq'),
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    situation BOOLEAN NOT NULL DEFAULT TRUE,
    priority VARCHAR(255) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (user_id) REFERENCES "user"(id)
);

INSERT INTO role (name, description) VALUES ('ADMIN', 'Administrator');
INSERT INTO role (name, description) VALUES ('USER', 'User');

