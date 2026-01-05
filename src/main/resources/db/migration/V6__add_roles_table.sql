create table roles(
    id integer auto_increment primary key,
    name varchar(30) not null,
    constraint uq_roles_name unique (name)
);

create table user_roles(
    user_id bigint not null,
    role_id int not null,
    constraint pk_user_roles_user_id_role_id primary key (user_id, role_id),
    constraint fk_user_roles_users foreign key (user_id) references users (id),
    constraint fk_user_roles_roles foreign key (role_id) references roles (id)
);