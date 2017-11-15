create table locations (
	id int not null AUTO_INCREMENT primary key,
	name varchar(50) not null,
	detail text not null,
	event_id varchar(10) not null,
	foreign key(event_id) references events(id)
);