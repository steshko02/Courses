CREATE TABLE lessons (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title varchar(50) NOT NULL,
    description varchar(1024),
    status smallint NOT NULL,
    sourceUrl varchar(128),
    duration integer not null,
    courseId  bigint NOT NULL,
    foreign key (courseId) references courses (id),
    resourceId bigint NOT NULL,
    foreign key (resourceId) references resources (id)
);