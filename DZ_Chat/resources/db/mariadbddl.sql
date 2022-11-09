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
