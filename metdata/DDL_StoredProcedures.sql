create database PACKET_FILTER;

/* admin user and permissions*/

grant all on PACKET_FILTER.* To admin@localhost identified by 'admin' WITH GRANT OPTION;

/*retrive stored procs*/

SHOW PROCEDURE STATUS;

SET FOREIGN_KEY_CHECKS=0;
drop table if exists PACKET;
drop table if exists MAPPINGS;
SET FOREIGN_KEY_CHECKS=1;


/*==============================================================*/
/* Table: PACKET_MAPPING                      */
/*==============================================================*/
create table PACKET
(
   PACKET_ID int not null AUTO_INCREMENT,
   PACKET_TYPE varchar(30) not null,
   TARGET_PATH varchar(1000),
   primary key (PACKET_ID)
);

alter table PACKET comment 'A mapping of a packet into the target location with expected result';

/*==============================================================*/
/* Table: MAPPINGS                      */
/*==============================================================*/
create table MAPPINGS
(
   MAPPING_ID           int not null AUTO_INCREMENT,
   PACKET_ID	int not null,
   TRANSFORMATION_HANDLER varchar(3000) not null,
   INPUT_PARAMETERS     varchar(3000),
   TRANSFORMATION_LEVEL int,
 primary key (MAPPING_ID)
);

alter table MAPPINGS comment 'Identifies a Java object and parameters that are applied to each mapping';

/*==============================================================*/
/* Foriegn Keys                                           */
/*==============================================================*/

alter table MAPPINGS add constraint FK_CSC1CTMC foreign key (PACKET_ID) references PACKET (PACKET_ID) on delete restrict on update restrict;


/*==============================================================*/
/* Stored procedures                                            */
/*==============================================================*/

/* Mappings table stored proc*/
DELIMITER $$
DROP PROCEDURE IF EXISTS getMappingsTableData $$
CREATE PROCEDURE getMappingsTableData()
BEGIN
	SELECT MAPPING_ID, PACKET_ID, TRANSFORMATION_HANDLER, INPUT_PARAMETERS, TRANSFORMATION_LEVEL
	FROM MAPPINGS;
END $$
DELIMITER ;

/* Packet table stored proc*/

DELIMITER $$
DROP PROCEDURE IF EXISTS getPacketTableData $$
CREATE PROCEDURE getPacketTableData()
BEGIN
	SELECT PACKET_ID, PACKET_TYPE, TARGET_PATH
	FROM PACKET;
END $$
DELIMITER ;

/* Single packet enry stored proc*/

DELIMITER $$
DROP PROCEDURE IF EXISTS getPacketDetails $$
CREATE PROCEDURE getPacketDetails(
IN p_PACKET_ID int
)
BEGIN
	SELECT PACKET_ID, PACKET_TYPE, TARGET_PATH
	FROM PACKET WHERE
	PACKET_ID = p_PACKET_ID;
END $$
DELIMITER ;

/* Single Mappings enry stored proc*/

DELIMITER $$
DROP PROCEDURE IF EXISTS getPacketMappingDetails $$
CREATE PROCEDURE getPacketMappingDetails(
IN p_PACKET_ID int
)
BEGIN
	SELECT MAPPING_ID, PACKET_ID, TRANSFORMATION_HANDLER, INPUT_PARAMETERS, TRANSFORMATION_LEVEL
	FROM MAPPINGS WHERE
	PACKET_ID = p_PACKET_ID;
END $$
DELIMITER ;



/* Inserts into Tables*/
INSERT INTO PACKET(PACKET_ID,PACKET_TYPE,TARGET_PATH) values (1,"Type1","/esds/pat/filter/");

INSERT INTO MAPPINGS(MAPPING_ID,PACKET_ID,TRANSFORMATION_HANDLER, INPUT_PARAMETERS, TRANSFORMATION_LEVEL) values (10,1,"BadIPDetector","badIPFile",1);



