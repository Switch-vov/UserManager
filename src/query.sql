-- oracle
create table users
(
  id number primary key,  --id
  username varchar2(32) not null, --�û���
  email varchar2(64) not null,    --��������
  grade number default 1,         --�ȼ� 1->5 1Ϊ��ͨ�û���5Ϊ����Ա 
  passwd varchar2(32) not null    --����
);

--�����������
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

--��ҳ���
select * from (select u.*,rownum rn from (select * from users order by id) u where rownum <= 6) where rn >=4;


--����������
create sequence users_seq
start with 100
increment by 1
minvalue 100
nomaxvalue
nocycle
nocache;