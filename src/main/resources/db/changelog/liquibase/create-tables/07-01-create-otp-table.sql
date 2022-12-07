CREATE TABLE otp (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    code integer NOT NULL,
    codeType varchar(6) NOT NULL,
    date_creation TIMESTAMP(14) DEFAULT CURRENT_TIMESTAMP,
    userId BIGSERIAL NOT NULL,
    foreign key (userId) references users(id)
);