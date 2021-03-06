package data;

public class DBInitiation {
	public boolean initiate() {
		DBManagement db = new DBManagement();
		boolean flag = true;
		clearAll();
		flag = flag && db.update("create table FACILITYRESOURCE(ID INT NOT NULL,NAME VARCHAR(255),PRIMARY KEY (ID));");
		flag = flag && db.update(
				"create table FUNDINGRESOURCE(AMOUNT INT NOT NULL DEFAULT '0',UNIT VARCHAR(255) NOT NULL,PRIMARY KEY(UNIT));");
		flag = flag && db.update(
				"create table USER(USERNAME VARCHAR(255) NOT NULL,PASSWORD VARCHAR(255) NOT NULL,NAME VARCHAR(255),ROLE VARCHAR(255),PRIMARY KEY(USERNAME));");
		flag = flag && db.update(
				"create table SOFTWARESYSTEM(NAME VARCHAR(255) NOT NULL,DESCRIPTION VARCHAR(255),PRIMARY KEY(NAME));");
		flag = flag && db.update("create table TECHNOLOGY(TNAME VARCHAR(255) NOT NULL,PRIMARY KEY(TNAME));");
		flag = flag && db.update(
				"create table SOFTECH(SNAME VARCHAR(255) NOT NULL,TNAME VARCHAR(255) NOT NULL,PRIMARY KEY(SNAME, TNAME),FOREIGN KEY(SNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(TNAME) REFERENCES TECHNOLOGY(TNAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table PROCESS(ID int not null,SOFTWARESYSTEMNAME varchar(255) NOT NULL,FROM_DATE DATE,TO_DATE DATE,TYPE VARCHAR(255),primary key (ID),FOREIGN KEY(SOFTWARESYSTEMNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table HUMANRESOURCE(USERNAME VARCHAR(255) NOT NULL,FROM_DATE DATE NOT NULL,TO_DATE DATE NOT NULL,PRIMARY KEY(USERNAME, FROM_DATE, TO_DATE),FOREIGN KEY(USERNAME) REFERENCES USER(USERNAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table FACILITYRESOURCEALLOCATION(MODULENAME VARCHAR(255) NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,ID INT NOT NULL,TYPE VARCHAR(255) NOT NULL,FROM_DATE DATE NOT NULL,TO_DATE DATE NOT NULL,PRIMARY KEY(MODULENAME, PROJECTNAME, ID, FROM_DATE, TO_DATE),FOREIGN KEY(ID) REFERENCES FACILITYRESOURCE(ID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table HUMANRESOURCEALLOCATION(MODULENAME VARCHAR(255) NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,USERNAME VARCHAR(255) NOT NULL,TYPE VARCHAR(255) NOT NULL,FROM_DATE DATE NOT NULL,TO_DATE DATE NOT NULL,PRIMARY KEY(MODULENAME, PROJECTNAME, USERNAME, FROM_DATE, TO_DATE),FOREIGN KEY(USERNAME) REFERENCES HUMANRESOURCE(USERNAME) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table FUNDINGRESOURCEALLOCATION(MODULENAME VARCHAR(255) NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,UNIT VARCHAR(255) NOT NULL,AMOUNT INT NOT NULL,TYPE VARCHAR(255) NOT NULL,FROM_DATE DATE NOT NULL,TO_DATE DATE NOT NULL,PRIMARY KEY(MODULENAME, PROJECTNAME, UNIT, AMOUNT, FROM_DATE, TO_DATE),FOREIGN KEY(UNIT) REFERENCES FUNDINGRESOURCE(UNIT) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table ORGANIZATIONUNIT(ID INT NOT NULL,NAME VARCHAR(255) NOT NULL UNIQUE,USERNAME VARCHAR(255) NOT NULL,PRIMARY KEY(ID),FOREIGN KEY(USERNAME) REFERENCES USER(USERNAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table REQUIREDHUMANRESOURCE(HREQID INT NOT NULL,OID INT NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,HNUMBER INT NOT NULL,SPECIALTY VARCHAR(255),SDATE DATE,PRIORITY INT NOT NULL DEFAULT '3',PRIMARY KEY(HREQID),FOREIGN KEY(OID) REFERENCES ORGANIZATIONUNIT(ID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table REQUIREDFACILITYRESOURCE(FREQID INT NOT NULL,OID INT NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,FNUMBER INT NOT NULL,NAME VARCHAR(255) NOT NULL,SDATE DATE,PRIORITY INT NOT NULL DEFAULT '3',PRIMARY KEY(FREQID),FOREIGN KEY(OID) REFERENCES ORGANIZATIONUNIT(ID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table REQUIREDFUNDINGRESOURCE(FREQID INT NOT NULL,OID INT NOT NULL,PROJECTNAME VARCHAR(255) NOT NULL,AMOUNT INT NOT NULL,UNIT VARCHAR(255) NOT NULL,SDATE DATE,PRIORITY INT NOT NULL DEFAULT '3',PRIMARY KEY(FREQID),FOREIGN KEY(OID) REFERENCES ORGANIZATIONUNIT(ID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(PROJECTNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table SOFTWARESYSTEM_ORGANIZATIONUNIT(OID INT NOT NULL,SSNAME VARCHAR(255) NOT NULL,PRIMARY KEY(OID, SSNAME),FOREIGN KEY(OID) REFERENCES ORGANIZATIONUNIT(ID) ON UPDATE CASCADE ON DELETE CASCADE,FOREIGN KEY(SSNAME) REFERENCES SOFTWARESYSTEM(NAME) ON UPDATE CASCADE ON DELETE CASCADE);");
		flag = flag && db.update(
				"create table PENDINGUPDATE(OUSERNAME VARCHAR(255) NOT NULL,USERNAME VARCHAR(255) NOT NULL,PASSWORD VARCHAR(255) NOT NULL,NAME VARCHAR(255),ROLE VARCHAR(255),PRIMARY KEY(USERNAME));");
		flag = flag && db.update("insert into USER values(\'Admin\', \'Admin\', \'Admin\', \'MANAGER\');");
		return flag;
	}

	private void clearAll() {
		DBManagement db = new DBManagement();
		String[] queries = new String[] { "SET FOREIGN_KEY_CHECKS = 0;", "SET @tables = NULL;",
				"SELECT GROUP_CONCAT(table_schema, \'.\', table_name) INTO @tables FROM information_schema.tables WHERE table_schema = \'EMP\';",
				"SET @tables = CONCAT(\'DROP TABLE \', @tables);", "PREPARE stmt FROM @tables;", "EXECUTE stmt;",
				"DEALLOCATE PREPARE stmt;", "SET FOREIGN_KEY_CHECKS = 1;" };
		System.out.println(db.update(queries[0]));
		System.out.println(db.update(queries[1]));
		System.out.println(queries[2]);
		System.out.println(db.getQuery(queries[2]));
		System.out.println(db.update(queries[3]));
		System.out.println(db.update(queries[4]));
		System.out.println(db.execute(queries[5]));
		System.out.println(db.update(queries[6]));
		System.out.println(db.update(queries[7]));
	}
}