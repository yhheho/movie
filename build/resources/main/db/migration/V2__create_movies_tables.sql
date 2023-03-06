CREATE TABLE movies (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255),
  description TEXT,
  type VARCHAR(255) NOT NULL,
  release_date TIMESTAMP NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL
);

CREATE INDEX type_index ON movies (type);

INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov1', '', 'ACTION', CAST('1991-10-09' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov2', '', 'HORROR', CAST('1992-10-22' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov3', '', 'ROMANTIC', CAST('2002-03-09' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov4', '', 'DOCUMENT', CAST('2005-06-19' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov5', '', 'ACTION', CAST('2006-07-22' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov6', '', 'HORROR' , CAST('2007-01-05' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov7', '', 'ROMANTIC', CAST('2008-03-09' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov8', '', 'DOCUMENT', CAST('2009-07-26' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov9', '', 'ACTION', CAST('2012-11-18' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov10', '', 'HORROR', CAST('2022-12-25' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('mov11', '', 'HORROR', CAST('2022-10-25' AS TIMESTAMP), NOW(), NOW());
INSERT INTO movies (title, description, type, release_date, created_at, updated_at) VALUES ('Titanic', 'a movie about titanic', 'ROMANTIC', CAST('1997-10-25' AS TIMESTAMP), NOW(), NOW());


