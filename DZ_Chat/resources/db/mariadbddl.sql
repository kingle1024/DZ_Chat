--create db user
create user 'masamo'@'localhost' identified by 'kosa';

-- create table
CREATE TABLE `tb_log` (
   `LOGID` INT(11) NOT NULL AUTO_INCREMENT,
   `CREATEDATE` DATETIME NULL DEFAULT NULL,
   `MSG` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
   PRIMARY KEY (`LOGID`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `tb_member` (
   `userid` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
   `password` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
   `name` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
   `birth` VARCHAR(50) NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
   PRIMARY KEY (`userid`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `tb_delete_member` (
	`deleteno` INT(11) NOT NULL AUTO_INCREMENT,
	`userid` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`password` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`birth` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`deleteno`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
;

--create procedure
CREATE DEFINER=`root`@`localhost` PROCEDURE `SP_INSERT_DELETEMEMBER`(
	IN `p_userid` VARCHAR(50),
	IN `p_password` VARCHAR(50),
	IN `p_name` VARCHAR(50),
	IN `p_birth` VARCHAR(50),
	OUT `p_deleteno` INT
)
LANGUAGE SQL
NOT DETERMINISTIC
CONTAINS SQL
SQL SECURITY DEFINER
COMMENT ''
begin
	SELECT LAST_INSERT_ID() into p_deleteno; 
	
	insert into TB_DELETE_MEMBER(userid, password, name, birth)
	values(p_userid, p_password, p_name, p_birth);
end
