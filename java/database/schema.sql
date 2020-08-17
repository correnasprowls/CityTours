BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) UNIQUE NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

DROP TABLE IF EXISTS states;
CREATE TABLE states (
code char(2) primary key,
name varchar(64) not null
);

INSERT into states values
('AK', 'Alaska'),
('AL', 'Alabama'),
('AZ', 'Arizona'),
('AR', 'Arkansas'),
('CA', 'California'),
('CO', 'Colorado'),
('CT', 'Connecticut'),
('DE', 'Delaware'),
('DC', 'District of Columbia'),
('FL', 'Florida'),
('GA', 'Georgia'),
('HI', 'Hawaii'),
('ID', 'Idaho'),
('IL', 'Illinois'),
('IN', 'Indiana'),
('IA', 'Iowa'),
('KS', 'Kansas'),
('KY', 'Kentucky'),
('LA', 'Louisiana'),
('ME', 'Maine'),
('MD', 'Maryland'),
('MA', 'Massachusetts'),
('MI', 'Michigan'),
('MN', 'Minnesota'),
('MS', 'Mississippi'),
('MO', 'Missouri'),
('MT', 'Montana'),
('NE', 'Nebraska'),
('NV', 'Nevada'),
('NH', 'New Hampshire'),
('NJ', 'New Jersey'),
('NM', 'New Mexico'),
('NY', 'New York'),
('NC', 'North Carolina'),
('ND', 'North Dakota'),
('OH', 'Ohio'),
('OK', 'Oklahoma'),
('OR', 'Oregon'),
('PA', 'Pennsylvania'),
('PR', 'Puerto Rico'),
('RI', 'Rhode Island'),
('SC', 'South Carolina'),
('SD', 'South Dakota'),
('TN', 'Tennessee'),
('TX', 'Texas'),
('UT', 'Utah'),
('VT', 'Vermont'),
('VA', 'Virginia'),
('WA', 'Washington'),
('WV', 'West Virginia'),
('WI', 'Wisconsin'),
('WY', 'Wyoming');


DROP TABLE IF EXISTS coordinates;

CREATE TABLE coordinates (
coordinate_id serial primary key,
x decimal,
y decimal
);

DROP TABLE IF EXISTS location;

CREATE TABLE location (
location_id serial primary key,
address_line_one varchar(255),
city varchar(50),
state_code varchar(2),
zip_code varchar(30),
coordinate_id INT,
constraint fk_state_code foreign key (state_code) references states(code),
constraint fk_coordinate_id foreign key (coordinate_id) references coordinates(coordinate_id)
);

DROP TABLE IF EXISTS landmarks;

CREATE TABLE landmarks (
landmark_id serial primary key,
name varchar(50) NOT NULL,
description text,
location_id INT,
constraint fk_location_id foreign key (location_id) references location(location_id)
);

DROP TABLE IF EXISTS itineraries;

CREATE TABLE itineraries (
itinerary_id serial primary key,
username varchar(50),
landmark_id INT,
name VARCHAR(60),
color VARCHAR(10),
constraint fk_username foreign key (username) references users(username)

);


DROP TABLE IF EXISTS landmarks_itineraries; 

CREATE TABLE landmarks_itineraries (
landmark_id INT, 
itinerary_id INT,
PRIMARY KEY (landmark_id, itinerary_id),
constraint fk_itinerary_id foreign key (itinerary_id) references itineraries(itinerary_id),
constraint fk_landmark_id foreign key (landmark_id) references landmarks(landmark_id)
);

COMMIT TRANSACTION;
