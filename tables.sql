--TABLES

create table USERS (
  nameuser varchar(20) primary key,
  name varchar(40),
  email varchar(40),
  passwd varchar(100)
);

create table ROOMS (
  idroom varchar(10) primary key,
  admin varchar(40),
  CONSTRAINT fk_room_admin
    FOREIGN KEY(admin)
    REFERENCES users(nameuser)
);

create table FILES (
  idfile varchar(20) primary key,
  filename varchar(40),
  room varchar(10),
  CONSTRAINT fk_room_file
    FOREIGN KEY(room)
    REFERENCES rooms(idroom)
);

create table CODE_LINES(
	numline int,
	idfile varchar(20),
	code varchar(500),
	primary key(numline, idfile),
  CONSTRAINT fk_file_codelines
    FOREIGN KEY(idfile)
    REFERENCES files(idfile)
);

create table CHATS (
  idChat varchar(40) primary key,
  room varchar(10),
  CONSTRAINT fk_room_file
    FOREIGN KEY(room)
    REFERENCES rooms(idroom)
);

create table MESSAGES (
  idMessage varchar(40) primary key,
  author varchar(40),
  idChat varchar(40),
  content varchar(400),
  date Date,
  CONSTRAINT fk_user_message
    FOREIGN KEY(author)
    REFERENCES users(nameuser),
  CONSTRAINT fk_chat
    FOREIGN KEY(idChat)
    REFERENCES chats(idChat)
);