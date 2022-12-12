CREATE TABLE users_roles
(
    userId   bigint NOT NULL,
    roleId bigint NOT NULL,
    primary key (userId, roleId),
    foreign key (userId) references users (id),
    foreign key (roleId) references role(id)
);