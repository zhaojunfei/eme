CREATE DATABASE IF NOT EXISTS eme DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
grant all privileges on eme.* to eme@localhost identified by 'eme#1234';
grant all privileges on eme.* to eme@'%' identified by 'eme#1234';
grant create routine on eme.* to eme@'%';
grant alter  routine on eme.* to eme@'%';
grant execute on eme.* to eme@'%';