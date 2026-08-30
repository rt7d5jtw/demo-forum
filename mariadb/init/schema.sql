-- Users
create table users (
	id int not null primary key,
	email varchar(254) not null,
	username varchar(254) not null,
	password varchar(255) not null,
  role varchar(10),
  user_status varchar(40),
	created date,
	updated timestamp,
	lastloggedin timestamp,
	posts_amount int,
	version int default 0,
	unique (email),
	unique (username)
);

-- Authorities
create table authorities (
	id int not null primary key,
	email varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(email) references users(email),
	unique (email)
);
create unique index ix_auth_email on authorities (email,authority);

-- Categories
create table categories (
	id int not null primary key,
	topic varchar(100) not null,
	topic_description varchar(255) not null,
	amount_threads int not null,
	amount_posts int not null,
	created date,
	updated timestamp,
	unique (topic)
);

-- Threads
create table threads (
	id int not null primary key,
	category_id int not null,
	user_id int not null,
	subject varchar(100) not null,
  content text,
	amount_posts int not null,
	created datetime,
	updated timestamp,
  constraint fk_thread_category foreign key (category_id) references categories (id),
  constraint fk_thread_user foreign key (user_id) references users (id)
);

-- Replies to post in a thread or to the thread.
create table replies (
  id int not null primary key,
	thread_id int not null,
  user_id int not null,
  content text not null,
	created datetime,
	updated timestamp,
  constraint fk_reply_thread foreign key (thread_id) references threads (id),
	constraint fk_reply_user foreign key (user_id) references users (id)
);
