-------------------
--DROP TABLES
-------------------
DROP TABLE UserAccounts;

-------------------
--CREATE TABLES
-------------------
CREATE TABLE UserAccounts(
ditto_user_id serial PRIMARY KEY
, ditto_username varchar(15) UNIQUE NOT NULL
, ditto_password varchar(15) NOT NULL
, ditto_first_name varchar(100) NOT null
, ditto_family_name varchar(100) NOT null
, ditto_email varchar(150) UNIQUE NOT NULL

);

-------------------
--INITIALIZE TABLES
-------------------
INSERT INTO UserAccounts (ditto_username, ditto_password, ditto_first_name 
			, ditto_family_name, ditto_email)
VALUES ('Hero', '12345', 'Cloud', 'Strife', 'cloud@ditto.com');

INSERT INTO UserAccounts (ditto_username, ditto_password, ditto_first_name 
			, ditto_family_name, ditto_email)
VALUES ('daLion', 'password', 'Squall', 'Lionheart', 'squall@ditto.com');

INSERT INTO UserAccounts (ditto_username, ditto_password, ditto_first_name 
			, ditto_family_name, ditto_email)
VALUES ('bartender', 'dr1nk', 'Tifa', 'Lockheart', 'tifa@ditto.com');

INSERT INTO UserAccounts (ditto_username, ditto_password, ditto_first_name 
			, ditto_family_name, ditto_email)
VALUES ('TimberWolf', 'boom!', 'Rinoa', 'Heartilly', 'rinoa@gmail.com');

INSERT INTO UserAccounts (ditto_username, ditto_password, ditto_first_name 
			, ditto_family_name, ditto_email)
VALUES ('Bolt', 'z4p!', 'Lightning', 'Farron', 'zap@gmail.com');

-------------------
--CREATE VIEWS
-------------------
-------------------
--UPDATE TABLES
-------------------
-------------------
--SELECT TABLES
-------------------
