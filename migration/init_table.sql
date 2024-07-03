create table method_execution_time_log
(
    id          bigint generated always as identity primary key,
    duration    bigint,
    started_at  timestamp(6),
    finished_at timestamp(6),
    class_name  varchar(255),
    method_name varchar(255)
);

alter table method_execution_time_log
    owner to postgres;

