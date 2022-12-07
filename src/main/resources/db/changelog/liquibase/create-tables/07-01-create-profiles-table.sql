CREATE TABLE profiles (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    photoUrl varchar(128),
    department varchar(50),
    jobTitle varchar(50),
    other varchar(128),
    githubUrl varchar(128),
    foreign key (credId) references credantionals(id)
);