CREATE TABLE favorites (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  user_id INTEGER,
  movie_id INTEGER,
  movie_title VARCHAR(255) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (movie_id) REFERENCES movies(id)
);

INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (2, 1, 'mov1', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (2, 2, 'mov2', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (2, 3, 'mov3', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (2, 8, 'mov8', NOW(), NOW());

INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 4, 'mov4', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 5, 'mov5', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 6, 'mov6', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 7, 'mov7', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 8, 'mov8', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 9, 'mov9', NOW(), NOW());
INSERT INTO favorites (user_id, movie_id, movie_title, created_at, updated_at) VALUES (3, 10, 'mov10', NOW(), NOW());

