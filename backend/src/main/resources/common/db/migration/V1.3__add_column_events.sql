alter table events add created_at timestamp not null default current_timestamp;
alter table events add updated_at timestamp not null default current_timestamp on update current_timestamp;