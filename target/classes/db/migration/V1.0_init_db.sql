CREATE SEQUENCE IF NOT EXISTS users_seq START 1;

CREATE SEQUENCE IF NOT EXISTS post_seq START 1;

CREATE TABLE users (
                       id BIGINT DEFAULT nextval('users_seq') PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       mail VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE category (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL
);

CREATE TABLE post (
                      id SERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      text TEXT NOT NULL,
                      timestamp TIMESTAMPTZ DEFAULT NOW(),
                      user_id INT,
                      category_id INT,
                      FOREIGN KEY (user_id) REFERENCES users (id),
                      FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE comments (
                          id SERIAL PRIMARY KEY,
                          text TEXT NOT NULL,
                          timestamp TIMESTAMPTZ DEFAULT NOW(),
                          user_id INT,
                          post_id INT,
                          FOREIGN KEY (user_id) REFERENCES users (id),
                          FOREIGN KEY (post_id) REFERENCES post (id)
);
