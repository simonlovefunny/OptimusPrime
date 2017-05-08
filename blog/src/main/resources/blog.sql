SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user_info`;
create table user(
uid  INT  PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
username VARCHAR(20) NOT NULL COMMENT '�û���',
password VARCHAR(20) NOT NULL COMMENT '����'
)ENGINE=INNODB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `blog_info`;
create table blog(
bid  INT  PRIMARY KEY AUTO_INCREMENT COMMENT 'blogid',
title VARCHAR(50) NOT NULL COMMENT '����',
content text NOT NULL COMMENT '����',
userid INT NOT NULL COMMENT '�������û�id',
FOREIGN KEY (userid) references user(uid) on delete cascade on update cascade  )ENGINE=INNODB DEFAULT CHARSET=utf8