CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    title varchar(128) NOT NULL,
    description varchar(1024),
    date_send TIMESTAMP(14) NOT NULL,
    type integer NOT NULL
);