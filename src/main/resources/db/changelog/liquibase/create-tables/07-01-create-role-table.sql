CREATE TABLE role (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name varchar(12) NOT NULL unique default 'USER'
);