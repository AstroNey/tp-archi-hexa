create table person
(
    personId  int auto_increment
        primary key,
    name      varchar(30) not null,
    firstName varchar(40) not null,
    age       int         not null,
    check (`age` >= 0)
);

create table team
(
    teamId int auto_increment
        primary key,
    name   varchar(20) not null
);

create table members
(
    personId int not null,
    teamId   int not null,
    primary key (personId, teamId),
    constraint members_person_fk
        foreign key (personId) references person (personId)
            on delete cascade,
    constraint members_team_fk
        foreign key (teamId) references team (teamId)
            on delete cascade
);
