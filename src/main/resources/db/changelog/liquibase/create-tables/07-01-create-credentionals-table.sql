CREATE TABLE credantionals (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    email varchar(50) NOT NULL CHECK (email like '%[A-Z0-9][@][A-Z0-9]%[.][A-Z0-9]%') UNIQUE,
    password varchar(50) NOT NULL
);