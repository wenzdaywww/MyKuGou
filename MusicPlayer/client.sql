--歌曲分组表
drop table if exists tb_group;
create table tb_group (
	groupId integer not null unique primary key autoincrement,
	groupName varchar(20) not null
);
--分组里面的歌曲
drop table if exists tb_groupMusic;
create table tb_groupMusic (
	id integer not null unique primary key autoincrement,
	groupId int not null,
	musicId int not null	
);
--歌曲详细信息
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
 --插入歌曲分组
 insert into tb_group values (1,'默认列表');
 insert into tb_group values (null,'最近播放');
