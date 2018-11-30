--���������
drop table if exists tb_group;
create table tb_group (
	groupId integer not null unique primary key autoincrement,
	groupName varchar(20) not null
);
--��������ĸ���
drop table if exists tb_groupMusic;
create table tb_groupMusic (
	id integer not null unique primary key autoincrement,
	groupId int not null,
	musicId int not null	
);
--������ϸ��Ϣ
drop table if exists tb_musicInfo;
create table tb_musicInfo (
	musicId integer not null unique primary key autoincrement,
	musicName varchar(20) not null,
	singer varchar(20) not null,
	musicPath varchar not null,
	musicTime varchar(10) not null,
	playCount int not null,
	netMusic varchar(5) not null
 );
 --�����������
 insert into tb_group values (1,'Ĭ���б�');
 insert into tb_group values (null,'�������');
