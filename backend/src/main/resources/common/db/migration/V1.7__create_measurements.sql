create table measurements (
	id int not null AUTO_INCREMENT primary key,
	route_id int not null,
	path_id int not null,
	degree double not null default 0,
	is_start boolean not null default 0,
	is_goal boolean not null default 0,
	is_road boolean not null default 0,
	is_crossroad boolean not null default 0,
	train_data json not null,
	around_info text,
	foreign key(route_id) references routes(id)
);