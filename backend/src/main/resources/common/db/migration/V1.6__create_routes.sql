create table routes (
	id int not null AUTO_INCREMENT primary key,
	source_id int not null,
	destination_id int not null,
	event_id varchar(10) not null,
	foreign key(source_id) references locations(id),
	foreign key(destination_id) references locations(id),
	foreign key(event_id) references events(id)
);