    INSERT INTO users (id, mail, password, username)
    SELECT
        nextval('users_seq'),
        'user' || id || '@example.com',
        md5(random()::text),
        'username' || id
    FROM generate_series(1, 1000) AS id;

    INSERT INTO category (id, title)
    SELECT
        id,
        'Category' || id
    FROM generate_series(1, 1000) AS id;


    INSERT INTO post (id, title, text, user_id, category_id)
    SELECT
        nextval('post_seq'),
        'Title' || id,
        'Text' || id,
        floor(random() * 1000) + 1,
        floor(random() * 1000) + 1
    FROM generate_series(1, 1000) AS id;

    INSERT INTO comments (id, text, timestamp, user_id, post_id)
    SELECT
        nextval('comments_seq'),
        'Comment' || id,
        NOW(),
        floor(random() * 1000) + 1,
        floor(random() * 1000) + 1
    FROM generate_series(1, 1000) AS id;

