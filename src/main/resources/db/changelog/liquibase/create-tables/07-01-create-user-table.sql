CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    firstname varchar(50) NOT NULL,
    lastname varchar(50) NOT NULL,
    credId bigint NOT NULL unique,
    status INTEGER DEFAULT 0,
    foreign key (credId) references credentials(id),
    profileId bigint unique,
    foreign key (profileId) references profiles(id)
);