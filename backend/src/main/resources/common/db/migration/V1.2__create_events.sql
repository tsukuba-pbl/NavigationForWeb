create table events (
	id varchar(10) not null primary key,
	name text not null,
	description text,
	location text,
	start_date datetime not null,
	end_date datetime not null,
	user_id varchar(10) not null,
	foreign key(user_id) references users(id)
);