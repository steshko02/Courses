CREATE TABLE booking (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    userId    BIGSERIAL NOT NULL,
    foreign key (userId) references users (id),
    courseId  BIGSERIAL NOT NULL,
    foreign key (courseId) references courses (id),
    date_creation TIMESTAMP(14) DEFAULT CURRENT_TIMESTAMP,
    status smallint NOT NULL default 1
);