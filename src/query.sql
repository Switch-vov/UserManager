-- oracle
create table users
(
  id number primary key,  --id
  username varchar2(32) not null, --用户名
  email varchar2(64) not null,    --电子邮箱
  grade number default 1,         --等级 1->5 1为普通用户，5为管理员 
  passwd varchar2(32) not null    --密码
);

--插入测试数据
insert into users values(1,'zhangsan','zhangsan@switch.com',1,'123456');
insert into users values(2,'lisi','lisi@switch.com',1,'123456');
insert into users values(3,'wangwu','wangwu@switch.com',1,'123456');
insert into users values(4,'zhaoliu','zhaoliu@switch.com',1,'123456');
insert into users values(5,'switch','switch@switch.com',5,'switch');
insert into users values(6,'AAA','AAA@switch.com',1,'123456');
insert into users values(7,'BBB','BBB@switch.com',1,'123456');
insert into users values(8,'CCC','CCC@switch.com',1,'123456');
insert into users values(9,'DDD','DDD@switch.com',1,'123456');
insert into users values(10,'EEE','EEE@switch.com',1,'123456');
insert into users values(11,'FFF','FFF@switch.com',1,'123456');
insert into users values(12,'GGG','GGG@switch.com',1,'123456');
insert into users values(13,'HHH','HHH@switch.com',1,'123456');
insert into users values(14,'AAA','AAA@switch.com',1,'123456');
insert into users values(15,'BBB','BBB@switch.com',1,'123456');
insert into users values(16,'CCC','CCC@switch.com',1,'123456');
insert into users values(17,'DDD','DDD@switch.com',1,'123456');
insert into users values(18,'EEE','EEE@switch.com',1,'123456');
insert into users values(19,'FFF','FFF@switch.com',1,'123456');
insert into users values(20,'GGG','GGG@switch.com',1,'123456');
insert into users values(21,'HHH','HHH@switch.com',1,'123456');
insert into users values(22,'HHH','HHH@switch.com',1,'123456');

--分页语句
select * from (select u.*,rownum rn from (select * from users order by id) u where rownum <= 6) where rn >=4;


--序列自增长
create sequence users_seq
start with 100
increment by 1
minvalue 100
nomaxvalue
nocycle
nocache;