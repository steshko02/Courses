CREATE TABLE users_events (
    userId bigint NOT NULL,
    eventsId bigint NOT NULL,
    primary key (userId,eventsId),
    foreign key(userId) references users(id),
    foreign key(eventsId) references events(id)

);