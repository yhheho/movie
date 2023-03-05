CREATE TABLE users (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  UNIQUE(username)
);


INSERT INTO users (username, email, password, created_at, updated_at) VALUES ('admin', 'admin@monstarlab.com', '$2a$12$Glo/oXmFSmuBdBpiYhlDkeky.8mWcHvjQ0oYQfr1nNcyTs261CGmq', NOW(), NOW());
INSERT INTO users (username, email, password, created_at, updated_at) VALUES ('m1', 'm1@monstarlab.com', '$2a$12$Glo/oXmFSmuBdBpiYhlDkeky.8mWcHvjQ0oYQfr1nNcyTs261CGmq', NOW(), NOW());
INSERT INTO users (username, email, password, created_at, updated_at) VALUES ('m2', 'm2@monstarlab.com', '$2a$12$Glo/oXmFSmuBdBpiYhlDkeky.8mWcHvjQ0oYQfr1nNcyTs261CGmq', NOW(), NOW());


CREATE TABLE roles (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', NOW(), NOW());
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', NOW(), NOW());

create TABLE user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);





