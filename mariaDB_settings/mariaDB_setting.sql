-- AWS mariaDB 세팅에 사용했음!!

show character set like 'ut%';

CREATE DATABASE myDB
    CHARACTER SET 'utf8mb4';

-- ALTER DATABASE myDB
-- CHARACTER SET 'utf8mb4';

use myDB;

show databases;
show table status;
show create table;

-- 해당 데이터베이스가 어떻게 생성되어 있는지?
show create database myDB;

-- 전체 DB 정보 조회 (CHARSET 보려고 썼음)
select * from information_schema.SCHEMATA;

-- 유저 생성
CREATE USER 'poly'@'%' IDENTIFIED BY '1234';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, ALTER
      ON myDB.*
          to 'poly'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;

show create user 'poly'@'%';

alter user 'poly'@'%' IDENTIFIED by '1234';
-- 다 하고 연결이 안돼서... 위에 유저 설정부분 다시 했더니 됨!

SHOW VARIABLES LIKE 'CHAR%';