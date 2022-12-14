CREATE TABLE users_courses
(
    userId    bigint NOT NULL,
    coursesId bigint NOT NULL,
    roleId    bigint NOT NULL,
    primary key (userId, coursesId),
    foreign key (userId) references users (id),
    foreign key (coursesId) references courses (id),
    foreign key (roleId) references role (id)

);