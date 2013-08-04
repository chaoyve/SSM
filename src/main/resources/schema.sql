CREATE TABLE IF NOT EXISTS user (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	user_id VARCHAR(45) NOT NULL,
	first_name VARCHAR(300) NOT NULL,
	last_name VARCHAR(300) NOT NULL,
	password VARCHAR(100) NOT NULL,
	birthday DATETIME,
	create_by INT NOT NULL,
	create_at DATETIME NOT NULL,
	update_by INT NOT NULL,
	update_at DATETIME NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT uc_user_id UNIQUE (user_id)
);

-----------------------------=== WOW
CREATE TABLE IF NOT EXISTS wow_equipment (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	boss VARCHAR(30) NOT NULL,
	name VARCHAR(30) NOT NULL,
	create_by INT NOT NULL,
	create_at DATETIME NOT NULL,
	update_by INT NOT NULL,
	update_at DATETIME NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT uc_boss_name UNIQUE (boss, name)
);

INSERT INTO wow_equipment (boss, name, create_by, create_at, update_by, update_at)
VALUES
("乌总", "项链", 0, now(), 0, now()),
("乌总", "戒指", 0, now(), 0, now()),
("乌总", "腰带", 0, now(), 0, now()),
("乌总", "手套", 0, now(), 0, now()),
("乌总", "胸甲", 0, now(), 0, now()),
("纳总", "手套", 0, now(), 0, now()),
("纳总", "腿甲", 0, now(), 0, now()),
("炮姐", "头盔", 0, now(), 0, now()),
("炮姐", "鞋子", 0, now(), 0, now())

CREATE TABLE IF NOT EXISTS wow_role (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(300) NOT NULL,
	create_by INT NOT NULL,
	create_at DATETIME NOT NULL,
	update_by INT NOT NULL,
	update_at DATETIME NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT uc_name UNIQUE (name)
);
INSERT INTO wow_role (name, create_by, create_at, update_by, update_at)
VALUES
("大古法", 0, now(), 0, now()),
("大古德", 0, now(), 0, now()),
("大古圣", 0, now(), 0, now()),
("大古猎", 0, now(), 0, now()),
("大古奇", 0, now(), 0, now()),
("大古盗", 0, now(), 0, now()),
("小莫牧", 0, now(), 0, now()),
("小莫人", 0, now(), 0, now()),
("小莫萨", 0, now(), 0, now())

CREATE TABLE IF NOT EXISTS wow_role_week (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	role_id INT NOT NULL,
	boss VARCHAR(300) NOT NULL,
	kill_week INT NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT uc_role_boss_week UNIQUE (role_id, boss, kill_week)
);

CREATE TABLE IF NOT EXISTS wow_role_equipment (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	role_id INT NOT NULL,
	equipment_id INT NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT uc_role_equipment UNIQUE (role_id, equipment_id)
);

