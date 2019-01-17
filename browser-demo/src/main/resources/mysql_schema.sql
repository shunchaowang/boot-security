CREATE
DATABASE
IF
NOT
EXISTS
`ird-security`
DEFAULT
CHARACTER
SET utf8;

USE
`ird-security`;

create table if not exists users
(
  username varchar(50)  not null primary key,
  password varchar(100) not null,
  enabled  boolean      not null
);
create table if not exists authorities
(
  username  varchar(50) not null,
  authority varchar(50) not null,
  constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);

create table if not exists persistent_logins
(
  username  varchar(50) not null,
  series    varchar(64) primary key,
  token     varchar(64) not null,
  last_used timestamp   not null
);
