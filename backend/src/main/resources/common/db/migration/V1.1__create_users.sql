create table users (
	id varchar(10) not null primary key,
	name text not null,
	email text,
	password varchar(256) not null
);