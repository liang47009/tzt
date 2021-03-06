 FROM_DB GAME_HERO_EQUIP N_STRENGTHEN_LEVEL int GOTO_DB GAME_HERO_EQUIP N_STRENGTHEN_LEVEL int FROM_DB 	GAME_ROLE S_OLD_SERVERID varchar(200) GOTO_DB 	GAME_ROLE S_OLD_SERVERID varchar(200) FROM_DB 	GAME_ROLE N_USE_NEWCARD tinyint GOTO_DB 	GAME_ROLE N_USE_NEWCARD tinyint FROM_DB 	GAME_ROLE N_REPLACE_POINT numeric(38, 3) GOTO_DB 	GAME_ROLE N_REPLACE_POINT numeric(38, 3) FROM_DB 	GAME_ROLE S_NPC_ROLE_LEVEL varchar(100) GOTO_DB 	GAME_ROLE S_NPC_ROLE_LEVEL varchar(100) FROM_DB 	GAME_ROLE 	N_OLDRACE tinyint GOTO_DB 	GAME_ROLE 	N_OLDRACE tinyint FROM_DB GAME_MAP_AREA N_DECL_KINGDOM_ID int GOTO_DB GAME_MAP_AREA N_DECL_KINGDOM_ID int FROM_DB GAME_MAP_AREA D_DECL_TIME datetime GOTO_DB GAME_MAP_AREA D_DECL_TIME datetimeqif not exists (select 1 from sysobjects where id = object_id('GAME_ARMY_PROP_USE') and type = 'U')
create table GAME_ARMY_PROP_USE (
	N_ID                 bigint               identity,
	N_ITEM_TYPE          int                  not null,
	S_ITEM_FUNCTION      varchar(20)          not null,
	N_ROLE_ID            int                  not null,
	N_ARMY_ID            bigint               not null,
	N_EFFECT_TIME        int                  not null,
	N_EFFECT_TIME_USE    int                  not null,
	D_TIME               datetime             not null,
	constraint PK_GAME_ARMY_PROP_USE primary key  (N_ID)
)
 FROM_DB GOTO_DB�if not exists (select 1 from sysobjects where id = object_id('GAME_TOLLGATE_ACHIEVE') and type = 'U')
create table GAME_TOLLGATE_ACHIEVE (
	N_ID                 bigint               identity,
	N_ACHIEVE_ID         int                  not null,
	N_ROLE_ID            int                  not null,
	N_SIGN_ID            tinyint              null,
	constraint PK_GAME_TOLLGATE_ACHIEVE primary key  (N_ID)
)
 FROM_DB GOTO_DB