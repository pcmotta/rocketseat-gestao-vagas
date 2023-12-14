create table company (
    id char(36) not null,
    name varchar(255) not null,
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(100) not null,
    website varchar(255),
    description text,
    created_at date not null default (current_date),
    primary key (id)
) engine=InnoDB default charset=utf8mb4;