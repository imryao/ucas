drop table if exists `ucas_user`;
create table `ucas_user`
(
    `id`             bigint unsigned primary key auto_increment,
    `ucas_id`        varchar(16) not null,
    `me_vjuid`       int         not null,
    `me_vjvd`        varchar(32) not null,
    `workflow_vjuid` int         not null,
    `workflow_vjvd`  varchar(32) not null,
    `inserted_at`    timestamp   not null default CURRENT_TIMESTAMP,
    `updated_at`     timestamp   not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
    unique `unique_ucas_id` (`ucas_id`);
)