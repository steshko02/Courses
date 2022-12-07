CREATE TABLE courses (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title varchar(50) NOT NULL unique,
    description varchar(1024),
    status smallint NOT NULL,
    size smallint,
    type smallint NOT NULL
);