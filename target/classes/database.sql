drop table if exists Member;

create table if not exists Member()
loginId varchar(8) primary key,
password varchar(8) not null,
name varchar(15) not null,
phone varchar(9),
email varchar(20)
;