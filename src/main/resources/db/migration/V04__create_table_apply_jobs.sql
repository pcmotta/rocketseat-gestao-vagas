ALTER TABLE candidate
    CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

create table applyJobs
(
    id           char(36) not null,
    candidate_id char(36) not null,
    job_id       char(36) not null,
    created_at   date     not null default (current_date),
    primary key (id),
    foreign key (candidate_id) references candidate (id),
    foreign key (job_id) references job (id)
) engine = InnoDB
  default charset = utf8mb4;