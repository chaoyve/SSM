drop table if exists user;
create table user (
	id int unsigned not null auto_increment,
	user_id varchar(45) not null,
	first_name varchar(300) not null,
	last_name varchar(300) not null,
	password varchar(100) not null,
	birthday datetime,
	create_by int not null,
	create_at datetime not null,
	update_by int not null,
	update_at datetime not null,
	primary key (id),
	constraint uc_user_id unique (user_id)
);

-----------------------------=== wow
drop table if exists wow_equipment;
create table wow_equipment (
	id int unsigned not null auto_increment,
	boss varchar(30) not null,
	name varchar(30) not null,
	create_by int not null,
	create_at datetime not null,
	update_by int not null,
	update_at datetime not null,
	primary key (id),
	constraint uc_boss_name unique (boss, name)
);

insert into wow_equipment (boss, name, create_by, create_at, update_by, update_at)
values
('乌总', '项链', 0, now(), 0, now()),
('乌总', '戒指', 0, now(), 0, now()),
('乌总', '腰带', 0, now(), 0, now()),
('乌总', '手套', 0, now(), 0, now()),
('乌总', '胸甲', 0, now(), 0, now()),
('纳总', '手套', 0, now(), 0, now()),
('纳总', '腿甲', 0, now(), 0, now()),
('炮姐', '头盔', 0, now(), 0, now()),
('炮姐', '鞋子', 0, now(), 0, now());

drop table if exists wow_role;
create table wow_role (
	id int unsigned not null auto_increment,
	name varchar(300) not null,
	create_by int not null,
	create_at datetime not null,
	update_by int not null,
	update_at datetime not null,
	primary key (id),
	constraint uc_name unique (name)
);
insert into wow_role (name, create_by, create_at, update_by, update_at)
values
('大古法', 0, now(), 0, now()),
('大古德', 0, now(), 0, now()),
('大古圣', 0, now(), 0, now()),
('大古猎', 0, now(), 0, now()),
('大古奇', 0, now(), 0, now()),
('大古盗', 0, now(), 0, now()),
('小莫牧', 0, now(), 0, now()),
('小莫人', 0, now(), 0, now()),
('小莫萨', 0, now(), 0, now());

drop table if exists wow_role_week;
create table wow_role_week (
	id int unsigned not null auto_increment,
	role_id int not null,
	boss varchar(300) not null,
	kill_week int not null,
	primary key (id),
	constraint uc_role_boss_week unique (role_id, boss, kill_week)
);

drop table if exists wow_role_equipment;
create table wow_role_equipment (
	id int unsigned not null auto_increment,
	role_id int not null,
	equipment_id int not null,
	primary key (id),
	constraint uc_role_equipment unique (role_id, equipment_id)
);

