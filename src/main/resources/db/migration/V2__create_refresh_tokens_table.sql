create table refresh_tokens(
    id bigint auto_increment primary key,
    token varchar(255) not null,
    created_at datetime not null,
    expires_at datetime not null,
    is_revoked boolean not null default false,
    user_id int not null,
    constraint uq_refresh_tokens_token unique (token),
    constraint ck_expires_at_after_created_at check (expires_at > created_at),
    constraint fk_refresh_tokens_users foreign key (user_id) references users (id)
)