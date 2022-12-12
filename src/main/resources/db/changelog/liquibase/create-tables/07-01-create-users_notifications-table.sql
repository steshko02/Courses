CREATE TABLE users_notifications
(
    userId   bigint NOT NULL,
    notificationId bigint NOT NULL,
    primary key (userId, notificationId),
    foreign key (userId) references users (id),
    foreign key (notificationId) references notifications(id)

);