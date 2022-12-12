CREATE TABLE works_reports (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    rating INTEGER,
    comment varchar(1024),
    status smallint NOT NULL DEFAULT 0,
    sourceUrl varchar(128),
    userId    bigint NOT NULL,
    foreign key (userId) references users (id),
    lectorId    bigint,
    foreign key (lectorId) references users (id),
    workId  bigint NOT NULL unique,
    foreign key (workId) references works(id)
);