create table beacons (
  id int not null AUTO_INCREMENT primary key,
  event_id varchar(10) not null,
  foreign key(event_id) references events(id),
  uuid varchar(50) not null,
  minor_id int not null
);