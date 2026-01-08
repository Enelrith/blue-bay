create table booking_fees(
    id bigint auto_increment primary key,
    type varchar(50) not null,
    amount decimal(10, 3) not null,
    description varchar(255) not null,
    constraint ck_amount_positive check (amount > 0)
);

alter table bookings
    add column taxes decimal(10, 2) not null,
    add column total_climate_fee decimal(10, 2) not null;

alter table bookings
    add constraint ck_taxes_positive check (taxes > 0),
    add constraint ck_total_climate_fee_positive check (total_climate_fee > 0)