CREATE TABLE users_events (
    userId BIGSERIAL NOT NULL,
    eventsId BIGSERIAL NOT NULL,
    primary key (userId,eventsId),
    foreign key(userId) references users(id),
    foreign key(eventsId) references events(id)

);