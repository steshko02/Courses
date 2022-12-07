CREATE TABLE users_roles
(
    userId   BIGSERIAL NOT NULL,
    roleId BIGSERIAL NOT NULL,
    primary key (userId, roleId),
    foreign key (userId) references users (id),
    foreign key (roleId) references role(id)
);