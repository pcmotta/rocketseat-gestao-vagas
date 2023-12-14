create table job
(
    id          char(36)     not null,
    description text         not null,
    benefits    varchar(255) not null,
    level       varchar(255) not null,
    company_id  char(36)     not null,
    created_at  date         not null default (current_date),
    primary key (id),
    foreign key (company_id) references company (id)
) engine = InnoDB
  default charset = utf8mb4;