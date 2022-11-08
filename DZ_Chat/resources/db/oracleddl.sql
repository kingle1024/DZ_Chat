-- create db user
alter session set "_ORACLE_SCRIPT"=true;
create user masamo identified by kosa;
grant connect, resource, dba to masamo;

-- create log sequence
create sequence seq_logid increment by 1;

-- create table
create table tb_member (
	userid varchar2(50) primary key
	, password varchar2(50)
	, name varchar2(50)
	, birth varchar2(50)
);
create table tb_log (
	logid number primary key
	, createdate date
	, msg varchar2(4000)
);

-- find member by userid
create or replace sp_find_member
(
	p_userid in tb_member.userid%type
) as
begin
	select * from tb_member where userid = p_userid;
end sp_find_member;

-- insert member procedure
create or replace sp_insert_member
(
	p_userid in tb_member.userid%type,
	p_password in tb_member.password%type,
	p_name in tb_member.name%type,
	p_birth in tb_member.birth%type
) as
begin
	insert into tb_member (userId, password, name, birth) values (p_userid, p_password, p_pname, p_birth);
end sp_insert_member;

-- update password of member procedure
create or replace sp_update_member
(
	p_new_password in tb_member.password%type,
	p_userid in tb_member.userid%type
) as
begin
	update tb_member set password = p_new_password where userid = p_userid;
end sp_update_member;

-- delete member procedure
create or replace sp_delete_member
(
	p_userid in tb_member.userid%type
) as
begin
	delete from tb_member where userid = p_userid;
end sp_delete_member

-- insert log procedure
create or replace sp_insert_log
(
  p_createdate in tb_log.createdate%type
, p_msg in tb_log.msg%type  
, p_logid out tb_log.logid%type
) as
begin
	select seq_logid.nextval into p_logid from dual;
	insert into tb_log (logid, createdate, msg) values (p_logid, p_createdate, p_msg);
end sp_insert_log;