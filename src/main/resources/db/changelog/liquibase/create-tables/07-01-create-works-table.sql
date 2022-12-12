CREATE TABLE works (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title varchar(50) NOT NULL,
    description varchar(1024),
    status smallint NOT NULL,
    sourceUrl varchar(128),
    lessonId  BIGSERIAL NOT NULL unique,
    deadline TIMESTAMP(14) NOT NULL,
    foreign key (lessonId) references lessons (id)
);

