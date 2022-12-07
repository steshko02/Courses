CREATE TABLE events (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    subject varchar(128) NOT NULL,
    date TIMESTAMP(14) NOT NULL,
    message varchar(1024)
);