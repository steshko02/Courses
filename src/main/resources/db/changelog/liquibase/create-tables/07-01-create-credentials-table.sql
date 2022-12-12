CREATE TABLE credentials (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    email varchar(50) NOT NULL UNIQUE,
    password varchar(128) NOT NULL
);
-- CHECK (email like '%[A-Z0-9][@][A-Z0-9]%[.][A-Z0-9]%')