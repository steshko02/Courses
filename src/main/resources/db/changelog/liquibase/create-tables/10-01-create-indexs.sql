CREATE UNIQUE INDEX cred_email
    ON credentials (email);

CREATE INDEX user_lastname
    ON users(lastname);

CREATE INDEX lessons_title
    ON lessons(title);

CREATE INDEX courses_title
    ON courses(title);