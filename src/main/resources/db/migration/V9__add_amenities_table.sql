create table amenities(
    id int auto_increment primary key,
    name varchar(100) not null,
    constraint uq_amenities_name unique (name)
);

create table property_amenities(
    id int auto_increment primary key,
    quantity int null,
    property_id int not null,
    amenity_id int not null,
    constraint ck_property_amenities_quantity_positive check (quantity > 0),
    constraint fk_property_amenities_properties foreign key (property_id) references properties (id),
    constraint fk_property_amenities_amenities foreign key (amenity_id) references amenities (id),
    constraint uq_property_amenities_property_id_amenity_id unique (property_id, amenity_id)
);