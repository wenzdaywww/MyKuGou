--�û���Ϣ��
drop table if exists tb_user;
create table tb_user(
	userId integer not null  unique check (userId>9999) primary key autoincrement,
	pwd  varchar(20) not null,
	name varchar(20) not null
);
--�û��ղر�
drop table if exists tb_collect;
create table tb_collect(
	collectId integer not null  unique primary key autoincrement,
	name varchar(20) not null,
	userId int not null
);
--�ղ�����ĸ�����
drop table if exists tb_collectMusic;
create table tb_collectMusic(
	id integer not null  unique primary key autoincrement,
	collectId int not null,
	musicId int not null
);
--���������
drop table if exists tb_class;
create table tb_class(
	classId integer not null  unique primary key autoincrement,
	name varchar(20) not null
);
--�����еĸ���
drop table if exists tb_classMusic;
create table tb_classMusic(
	id integer not null  unique primary key autoincrement,
	classId int not null,
	musicId int not null
);
--������Ϣ��
drop table if exists tb_musicInfo;
create table tb_musicInfo(
	musicId integer not null unique primary key autoincrement,
	musicName varchar(20) not null,
	singer varchar(20) not null,
	musicPath varchar not null,
	musicTime varchar(10) not null,
	playCount int not null,
	netMusic varchar(5) not null
);
--�û���Ϣ
insert into tb_user values(10000,'www12345','����Ա���');
insert into tb_user values(null,'www12345','����');
insert into tb_user values(null,'www12345','����');
insert into tb_user values(null,'www12345','����');
--�û��ղر�
insert into tb_collect values(1,'��ϲ����',10000);
insert into tb_collect values(null,'��ϲ����',10001);
insert into tb_collect values(null,'��ϲ����',10002);
insert into tb_collect values(null,'��ϲ����',10003);
--�ղر����
insert into tb_collectMusic values(1,1,1);
insert into tb_collectMusic values(null,1,2);
insert into tb_collectMusic values(null,1,3);
insert into tb_collectMusic values(null,1,4);
insert into tb_collectMusic values(null,1,5);
insert into tb_collectMusic values(null,1,6);
insert into tb_collectMusic values(null,1,7);
insert into tb_collectMusic values(null,1,8);
insert into tb_collectMusic values(null,2,1);
insert into tb_collectMusic values(null,2,2);
insert into tb_collectMusic values(null,2,3);
insert into tb_collectMusic values(null,2,4);
insert into tb_collectMusic values(null,2,5);
insert into tb_collectMusic values(null,2,6);
insert into tb_collectMusic values(null,2,7);
insert into tb_collectMusic values(null,2,8);
insert into tb_collectMusic values(null,3,1);
insert into tb_collectMusic values(null,3,2);
insert into tb_collectMusic values(null,3,3);
insert into tb_collectMusic values(null,3,4);
insert into tb_collectMusic values(null,3,5);
insert into tb_collectMusic values(null,3,6);
insert into tb_collectMusic values(null,3,7);
insert into tb_collectMusic values(null,3,8);
insert into tb_collectMusic values(null,4,1);
insert into tb_collectMusic values(null,4,2);
insert into tb_collectMusic values(null,4,3);
insert into tb_collectMusic values(null,4,4);
insert into tb_collectMusic values(null,4,5);
insert into tb_collectMusic values(null,4,6);
insert into tb_collectMusic values(null,4,7);
insert into tb_collectMusic values(null,4,8);
--���ַ����б�
insert into tb_class values(1,'����');
insert into tb_class values(null,'�˸�');
insert into tb_class values(null,'����');
--���ַ��������
insert into tb_classMusic values(1,1,1);
insert into tb_classMusic values(null,1,2);
insert into tb_classMusic values(null,1,3);
insert into tb_classMusic values(null,2,4);
insert into tb_classMusic values(null,2,5);
insert into tb_classMusic values(null,2,6);
insert into tb_classMusic values(null,3,7);
insert into tb_classMusic values(null,3,8);
--������Ϣ
insert into tb_musicInfo values(1,'��������','ׯ����','F:\Music\ׯ���� - ��������.mp3','04:55',0,'��������');
insert into tb_musicInfo values(null,'ֱ����һ��','��ϧ��','F:\Music\��ϧ�� - ֱ����һ��(1).mp3','04:29',0,'��������');
insert into tb_musicInfo values(null,'������ϵ','����Alex','F:\Music\����Alex - ������ϵ.mp3','03:24',0,'��������');
insert into tb_musicInfo values(null,'����','����','F:\Music\���� - ����.mp3','04:11',0,'��������');
insert into tb_musicInfo values(null,'��̫��','�����','F:\Music\����� - ��̫��.mp3','03:36',0,'��������');
insert into tb_musicInfo values(null,'��ɫ����','�κ���','F:\Music\�κ��� - ��ɫ����.mp3','04:48',0,'��������');
insert into tb_musicInfo values(null,'ҹɫ','����/������','F:\Music\���Ρ������� - ҹɫ.mp3','03:07',0,'��������');
insert into tb_musicInfo values(null,'�ֹ���û��','��ΰ��','F:\Music\��ΰ�� - �ֹ���û��.mp3','03:58',0,'��������');

