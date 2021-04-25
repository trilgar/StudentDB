create type gender_enum as enum ('MALE','FEMALE');
create type teacher_category as enum ('Assistant', 'Lecturer','SeniorLecturer', 'Docent','Professor');
create type asp_status as enum ('TRUE', 'FALSE');
create type s_work as enum ('CandidateWork','DissertationWork');
create type d_type as enum ('Lection', 'Seminar','LabWork', 'Consultancy','CourseWork' );
create type e_type as enum ('Exam', 'Credit');

create table groups
(
    id     serial primary key not null,
    name   varchar(20) UNIQUE NOT NULL,
    course integer            NOT NULL,
    year   integer            not null
);
create table faculty
(
    id   serial primary key not null,
    name varchar(20) UNIQUE NOT NULL
);
create table students
(
    id         serial primary key  not null,
    name       varchar(100) UNIQUE not null,
    id_group   integer             NOT NULL REFERENCES groups (id),
    id_faculty integer             NOT NULL REFERENCES faculty (id),
    stipendium integer,
    gender     gender_enum         NOT NULL,
    age        integer             NOT NULL,
    kids       integer
);

create table cathedra
(
    id   serial primary key not null,
    name varchar(20) UNIQUE NOT NULL
);
create table teachers
(
    id          serial primary key  not null,
    name        varchar(100) UNIQUE not null,
    id_faculty  integer             NOT NULL REFERENCES faculty (id),
    category    teacher_category    NOT NULL,
    year        integer             NOT NULL,
    wage        integer,
    is_asp      asp_status          NOT NULL,
    gender      gender_enum         NOT NULL,
    age         integer             NOT NULL,
    kids        integer,
    id_cathedra integer             NOT NULL REFERENCES cathedra (id)
);

create table science_work
(
    id          serial primary key not null,
    type        s_work             NOT NULL,
    id_teacher  integer            NOT NULL REFERENCES teachers (id),
    year        integer            NOT NULL,
    name        varchar(30) UNIQUE NOT NULL,
    description varchar(300)
);
create table discipline
(
    id         serial primary key not null,
    type       d_type             NOT NULL,
    id_teacher integer            NOT NULL REFERENCES teachers (id),
    id_group   integer            NOT NULL REFERENCES groups (id),
    name       varchar(20) UNIQUE NOT NULL,
    hours      integer            NOT NULL,
    course     integer            NOT NULL,
    semester   integer            NOT NULL
);
create table graduate_work
(
    id          serial primary key not null,
    id_student  integer UNIQUE     NOT NULL REFERENCES students (id),
    id_teacher  integer UNIQUE     NOT NULL REFERENCES teachers (id),
    year        integer            NOT NULL,
    name        varchar(20)        NOT NULL,
    description varchar(300)
);
create table exam
(
    id            serial primary key not null,
    type          e_type             NOT NULL,
    id_discipline integer            NOT NULL REFERENCES discipline (id),
    id_student    integer            NOT NULL REFERENCES students (id),
    description   varchar(300),
    mark          integer            NOT NULL,
    result        boolean            NOT NULL
);



create table users
(
    id       serial primary key  not null,
    email    varchar(100) UNIQUE not null,
    password varchar(300)        not null,
    name     varchar(100)        not null
);
