--用户信息表
drop table if exists tb_user;
create table tb_user(
	userId integer not null  unique check (userId>9999) primary key autoincrement,
	pwd  varchar(20) not null,
	name varchar(20) not null
);
--用户收藏表
drop table if exists tb_collect;
create table tb_collect(
	collectId integer not null  unique primary key autoincrement,
	name varchar(20) not null,
	userId int not null
);
--收藏里面的歌曲表
drop table if exists tb_collectMusic;
create table tb_collectMusic(
	id integer not null  unique primary key autoincrement,
	collectId int not null,
	musicId int not null
);
--分曲分类表
drop table if exists tb_class;
create table tb_class(
	classId integer not null  unique primary key autoincrement,
	name varchar(20) not null
);
--分类中的歌曲
drop table if exists tb_classMusic;
create table tb_classMusic(
	id integer not null  unique primary key autoincrement,
	classId int not null,
	musicId int not null
);
--歌曲信息表
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
--用户信息
insert into tb_user values(10000,'www12345','管理员身份');
insert into tb_user values(null,'www12345','张三');
insert into tb_user values(null,'www12345','李四');
insert into tb_user values(null,'www12345','王五');
--用户收藏表
insert into tb_collect values(1,'我喜欢的',10000);
insert into tb_collect values(null,'我喜欢的',10001);
insert into tb_collect values(null,'我喜欢的',10002);
insert into tb_collect values(null,'我喜欢的',10003);
--收藏表歌曲
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
--音乐分类列表
insert into tb_class values(1,'经典');
insert into tb_class values(null,'伤感');
insert into tb_class values(null,'流行');
--音乐分类歌曲表
insert into tb_classMusic values(1,1,1);
insert into tb_classMusic values(null,1,2);
insert into tb_classMusic values(null,1,3);
insert into tb_classMusic values(null,2,4);
insert into tb_classMusic values(null,2,5);
insert into tb_classMusic values(null,2,6);
insert into tb_classMusic values(null,3,7);
insert into tb_classMusic values(null,3,8);
--歌曲信息
insert into tb_musicInfo values(1,'爱情遗嘱','庄心妍','F:\Music\庄心妍 - 爱情遗嘱.mp3','04:55',0,'网络音乐');
insert into tb_musicInfo values(null,'直到那一天','刘惜君','F:\Music\刘惜君 - 直到那一天(1).mp3','04:29',0,'网络音乐');
insert into tb_musicInfo values(null,'不再联系','夏天Alex','F:\Music\夏天Alex - 不再联系.mp3','03:24',0,'网络音乐');
insert into tb_musicInfo values(null,'华生','徐良','F:\Music\徐良 - 华生.mp3','04:11',0,'网络音乐');
insert into tb_musicInfo values(null,'想太多','李玖哲','F:\Music\李玖哲 - 想太多.mp3','03:36',0,'网络音乐');
insert into tb_musicInfo values(null,'白色恋人','游鸿明','F:\Music\游鸿明 - 白色恋人.mp3','04:48',0,'网络音乐');
insert into tb_musicInfo values(null,'夜色','艺涛/梁剑东','F:\Music\艺涛、梁剑东 - 夜色.mp3','03:07',0,'网络音乐');
insert into tb_musicInfo values(null,'闹够了没有','赖伟锋','F:\Music\赖伟锋 - 闹够了没有.mp3','03:58',0,'网络音乐');

