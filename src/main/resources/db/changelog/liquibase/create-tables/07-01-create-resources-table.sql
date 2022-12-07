CREATE TABLE resources (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    size BIGINt NOT NULL,
    checksum varchar(128) NOT NULL,
    description varchar(128)
);