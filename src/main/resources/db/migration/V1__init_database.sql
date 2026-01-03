create table users(
    id int primary key auto_increment,
    email varchar(255) not null,
    password varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    verified_at timestamp null,
    constraint uq_users_email unique (email)
);

create table user_information(
    id int primary key auto_increment,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    date_of_birth date not null,
    nationality varchar(100) not null,
    id_document_type varchar(50) not null,
    id_document_number varchar(100) not null,
    completed_at timestamp default null,
    user_id int not null,
    constraint uq_user_information_document_number unique (id_document_number),
    constraint fk_user_information_user foreign key (user_id) references users(id)
);

create table properties(
    id int primary key auto_increment,
    atak_number varchar(255) not null,
    ama_number varchar(255) not null,
    square_meters decimal(8, 2) not null,
    type varchar(100) not null,
    street varchar(100) not null,
    city varchar(100) not null,
    postal_code varchar(20) not null,
    country varchar(100) not null,
    is_active boolean default true,
    constraint uq_properties_ama_number unique (ama_number),
    constraint ck_properties_square_meters_positive check (square_meters >= 0)
);

create table bookings(
    id int primary key auto_increment,
    user_id int not null,
    property_id int not null,
    check_in datetime not null,
    check_out datetime null,
    status varchar(30) not null,
    net_payment decimal(10, 2) null,
    total_payment decimal(10, 2) null,
    payment_type varchar(50) null,
    source varchar(100) not null,
    constraint fk_bookings_users foreign key (user_id) references users(id),
    constraint fk_bookings_properties foreign key (property_id) references properties(id),
    constraint uq_bookings_check_in_users_properties unique (user_id, property_id, check_in),
    constraint ck_bookings_net_payment_positive check (net_payment >= 0),
    constraint ck_bookings_total_payment_positive check (total_payment >= 0)
)