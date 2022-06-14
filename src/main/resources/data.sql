create table if not exists fcmtokens
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    platform      varchar(255),
    token         varchar(255),
    user_id       varchar(255)
);

alter table fcmtokens
    owner to harvestapi;

create table if not exists notifications
(
    id                varchar(255) not null
        primary key,
    created_time      timestamp    not null,
    deleted           boolean,
    modified_time     timestamp,
    body              varchar(255),
    notification_type integer,
    payload           varchar(255),
    status            integer,
    title             varchar(255),
    user_id           varchar(255)
);

alter table notifications
    owner to harvestapi;

create table if not exists org_projects
(
    id              varchar(255) not null
        primary key,
    created_time    timestamp    not null,
    deleted         boolean,
    modified_time   timestamp,
    client          varchar(255),
    end_date        timestamp,
    is_indefinite   boolean      not null,
    name            varchar(255),
    start_date      timestamp,
    organization_id varchar(255)
);

alter table org_projects
    owner to harvestapi;

create table if not exists organizations
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    name          varchar(255)
        constraint uk_p9pbw3flq9hkay8hdx3ypsldy
        unique,
    website       varchar(255),
    identifier    varchar(255)
);

alter table organizations
    owner to harvestapi;

create table if not exists praxisuser
(
    id                   varchar(255) not null
        primary key,
    created_time         timestamp    not null,
    deleted              boolean,
    modified_time        timestamp,
    avatar_url           varchar(255),
    email                varchar(255),
    first_name           varchar(255),
    last_name            varchar(255),
    org_id               varchar(255),
    password             varchar(255),
    reset_password_token varchar(255),
    verified             boolean
);

alter table praxisuser
    owner to harvestapi;

create table if not exists project_user_entry
(
    id            varchar(255)     not null
        primary key,
    created_time  timestamp        not null,
    deleted       boolean,
    modified_time timestamp,
    notes         varchar(255),
    org_id        uuid,
    time          double precision not null,
    user_id       uuid,
    work_type     uuid
);

alter table project_user_entry
    owner to harvestapi;

create table if not exists refreshtoken
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    expiry_date   timestamp    not null,
    token         varchar(255) not null
        constraint uk_or156wbneyk8noo4jstv55ii3
        unique,
    userid        varchar(255)
);

alter table refreshtoken
    owner to harvestapi;

create table if not exists role
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    name          varchar(255),
    user_id       varchar(255)
);

alter table role
    owner to harvestapi;

create table if not exists user_project_assignment
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    project_id    varchar(255),
    user_id       varchar(255)
);

alter table user_project_assignment
    owner to harvestapi;

create table if not exists user_work
(
    id            varchar(255) not null
        primary key,
    created_time  timestamp    not null,
    deleted       boolean,
    modified_time timestamp,
    note          varchar(255),
    project_id    varchar(255),
    user_id       varchar(255),
    work_date     timestamp,
    work_hours    real         not null
);

alter table user_work
    owner to harvestapi;

alter table user_work add column workType varchar(1) not null default '1';
