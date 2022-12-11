CREATE TABLE works_reports (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    rating INTEGER NOT NULL,
    comment varchar(1024),
    status smallint NOT NULL,
    sourceUrl varchar(128),
    userId    BIGSERIAL NOT NULL,
    foreign key (userId) references users (id),
    lectorId    BIGSERIAL NOT NULL,
    foreign key (lectorId) references users (id),
    workId  BIGSERIAL NOT NULL unique,
    foreign key (workId) references works(id)
);