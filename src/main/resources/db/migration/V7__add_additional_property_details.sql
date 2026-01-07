alter table properties
    add column latitude decimal(8, 6) not null,
    add column longitude decimal(9, 6) not null,
    add column nightly_rate decimal(10, 2) not null,
    add column cleaning_fee decimal(10, 2) not null,
    add constraint ck_nightly_rate_positive check (nightly_rate > 0),
    add constraint ck_cleaning_fee_positive check (cleaning_fee > 0);