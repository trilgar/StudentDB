create table users
(
    id           serial primary key  not null,
    email        varchar(100) UNIQUE not null,
    password     varchar(300)        not null,
    name         varchar(100)        not null
);