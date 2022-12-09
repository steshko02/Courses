CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    credId BIGSERIAL NOT NULL unique,
    status INTEGER NOT NULL DEFAULT 0,
    foreign key (credId) references credentials(id),
    profileId BIGSERIAL unique,
    foreign key (profileId) references profiles(id)
);