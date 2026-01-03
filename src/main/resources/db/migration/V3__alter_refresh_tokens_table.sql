alter table refresh_tokens
modify created_at datetime(3) not null,
modify expires_at datetime(3) not null