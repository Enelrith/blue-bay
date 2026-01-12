create table property_images(
    id int auto_increment primary key,
    name varchar(255) not null,
    property_id int not null,
    constraint uq_path_property_id unique (name, property_id),
    constraint fk_property_images_property_id foreign key (property_id) references properties (id)
)