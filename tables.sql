drop table code_lines;
drop table messages;
drop table files;
drop table chats;
drop table events;
drop table rooms;
drop table users;

--TABLES

create table USERS (
  nameuser varchar(20) primary key,
  name varchar(40),
  email varchar(40),
  passwd varchar(100)
);

create table ROOMS (
  idroom varchar(40) primary key,
  admin varchar(40),
  CONSTRAINT fk_room_admin
  FOREIGN KEY(admin)
  REFERENCES users(nameuser)
);

create table EVENTS (
  idevent integer primary key,
  creation_date date,
  idroom varchar(40),
  activity varchar(100),
  author varchar(20),
  type_evt varchar(10),
  CONSTRAINT fk_room_evt
  FOREIGN KEY(idroom)
  REFERENCES rooms(idroom),
  CONSTRAINT fk_user_evt
  FOREIGN KEY(author)
  REFERENCES users(nameuser)
);

create table FILES (
  idfile varchar(40) primary key,
  filename varchar(40),
  room varchar(40),
  CONSTRAINT fk_room_file
  FOREIGN KEY(room)
  REFERENCES rooms(idroom)
);

create table CODE_LINES(
  idcodeline varchar(60) primary key,
  numline int,
  idfile varchar(20),
  code varchar(500),
  CONSTRAINT fk_file_codelines
  FOREIGN KEY(idfile)
  REFERENCES files(idfile)
);

create table CHATS (
  idChat varchar(40) primary key,
  room varchar(40),
  CONSTRAINT fk_room_file
  FOREIGN KEY(room)
  REFERENCES rooms(idroom)
);

create table MESSAGES (
  idMessage integer primary key,
  author varchar(40),
  idChat varchar(40),
  content varchar(400),
  CONSTRAINT fk_user_message
  FOREIGN KEY(author)
  REFERENCES users(nameuser),
  CONSTRAINT fk_chat
  FOREIGN KEY(idChat)
  REFERENCES chats(idChat)
);
