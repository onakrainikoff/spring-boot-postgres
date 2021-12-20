create table element_group_types(
    id integer primary key not null,
    code varchar(50) not null
);
insert into element_group_types(id, code) values (1, 'TYPE1');
insert into element_group_types(id, code) values (2, 'TYPE2');

create table element_groups(
    id serial primary key not null,
    date_created timestamptz not null,
    group_type integer not null,
    name varchar(500) not null,
    constraint element_groups_to_element_group_types foreign key (group_type) references element_group_types(id)
);
create unique index udx_element_groups_name on element_groups (name);

create table elements(
    id serial primary key not null,
    date_created timestamptz not null,
    element_group integer not null,
    name varchar(500) not null,
    params hstore,
    tags varchar[],
    items jsonb,
    constraint elements_to_element_groups foreign key (element_group) references element_groups(id)
);
create index idx_elements_name on elements (name);

create table counters(
    id serial primary key not null,
    date_created timestamptz not null,
    name varchar(50) not null,
    value integer not null,
    date_last_modified timestamptz not null
);
create unique index udx_counters_name on counters (name);