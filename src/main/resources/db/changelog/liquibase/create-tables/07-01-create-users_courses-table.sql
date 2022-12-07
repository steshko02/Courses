CREATE TABLE users_courses
(
    userId    BIGSERIAL NOT NULL,
    coursesId BIGSERIAL NOT NULL,
    roleId    BIGSERIAL NOT NULL,
    primary key (userId, coursesId),
    foreign key (userId) references users (id),
    foreign key (coursesId) references courses (id),
    foreign key (roleId) references role (id)

);