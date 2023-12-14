create table candidate (
    id char(36) not null,
    name varchar(255) not null,
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(100) not null,
    description text,
    curriculum varchar(255),
    created_at date not null default (current_date),
    primary key (id)
) ENGINE=InnoDB default charset=utf8;