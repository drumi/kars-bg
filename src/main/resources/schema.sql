--Users

create table if not exists users(
	username text not null primary key,
	password text not null,
	enabled boolean not null
);

create table if not exists authorities (
	username text not null,
	authority text not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);


--Listsings

create table if not exists listing(
	id serial primary key,
	username text not null,
	title text not null, 
	price integer not null, 
	description text not null, 
	picture bytea not null,
	created_at timestamp not null default now()
);