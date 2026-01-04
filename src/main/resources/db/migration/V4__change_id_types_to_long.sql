alter table user_information
    drop constraint fk_user_information_user;

alter table bookings
    drop constraint fk_bookings_users;

alter table refresh_tokens
    drop constraint fk_refresh_tokens_users;

alter table users
modify column id bigint auto_increment;

alter table user_information
    modify column id bigint auto_increment,
    modify column user_id bigint not null,
    add constraint fk_user_information_users foreign key (user_id) references users (id);

alter table bookings
    modify column id bigint auto_increment,
    modify column user_id bigint not null,
    add constraint fk_bookings_users foreign key (user_id) references users(id);

alter table refresh_tokens
    modify column user_id bigint not null,
    add constraint fk_refresh_tokens_users foreign key (user_id) references users(id);