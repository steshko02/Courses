CREATE TABLE users_notifications
(
    userId   BIGSERIAL NOT NULL,
    notificationId BIGSERIAL NOT NULL,
    primary key (userId, notificationId),
    foreign key (userId) references users (id),
    foreign key (notificationId) references notifications(id)

);