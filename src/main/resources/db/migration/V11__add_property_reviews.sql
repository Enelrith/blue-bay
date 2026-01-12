create table reviews(
    id bigint auto_increment primary key,
    score tinyint not null,
    description nvarchar(255) null,
    created_at timestamp default current_timestamp,
    edited_at timestamp null,
    user_id bigint not null,
    property_id int not null,
    constraint ck_reviews_score_positive check (score > 0),
    constraint ck_reviews_score_max check (score <= 5),
    constraint fk_reviews_users foreign key (user_id) references users (id),
    constraint fk_reviews_properties foreign key (property_id) references properties (id),
    constraint uq_reviews_user_id_property_id unique (user_id, property_id)
);

create index idx_reviews_property_id on reviews(property_id);
create index idx_reviews_user_id on reviews(user_id);