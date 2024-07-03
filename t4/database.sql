DROP DATABASE IF EXISTS tests;

CREATE DATABASE tests;
USE tests;

CREATE TABLE Test(
    id INT PRIMARY KEY AUTO_INCREMENT,

    -- BIT[(M)]
    -- testBit BIT(20),
    -- TINYINT[(M)] [UNSIGNED] [ZEROFILL]
    testTinyInt TINYINT,
    -- BOOL, BOOLEAN = TINYINT(1)
    testBoolean BOOLEAN,
    -- SMALLINT[(M)] [UNSIGNED] [ZEROFILL]
    testSmallInt SMALLINT,
    -- MEDIUMINT[(M)] [UNSIGNED] [ZEROFILL]
    testMediumInt MEDIUMINT,
    -- INT[(M)] [UNSIGNED] [ZEROFILL]
    -- INTEGER[(M)] [UNSIGNED] [ZEROFILL]
    testInt INT,
    -- BIGINT[(M)] [UNSIGNED] [ZEROFILL]
    testBigInt BIGINT,
    -- DECIMAL[(M[,D])] [UNSIGNED] [ZEROFILL]
    testDecimal DECIMAL(5, 2),
    -- FLOAT(p) [UNSIGNED] [ZEROFILL]
    testFloat FLOAT,
    testFloat27 FLOAT(27),
    -- DOUBLE[(M,D)] [UNSIGNED] [ZEROFILL]
    testDouble DOUBLE,

    -- DATE
    testDate DATE,
    -- DATETIME[(fsp)]
    testDateTime DATETIME,
    -- TIMESTAMP[(fsp)]
    testTimestamp TIMESTAMP,
    -- TIME[(fsp)]
    testTime TIME,
    -- YEAR[(4)]
    -- testYear YEAR,

    -- [NATIONAL] CHAR[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]
    testChar CHAR,
    -- [NATIONAL] VARCHAR(M) [CHARACTER SET charset_name] [COLLATE collation_name]
    testVarChar VARCHAR(200),
    -- TINYTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
    testTinyText TINYTEXT,
    -- MEDIUMTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
    testMediumText MEDIUMTEXT,
    -- LONGTEXT [CHARACTER SET charset_name] [COLLATE collation_name]
    testLongText LONGTEXT,
    -- TEXT[(M)] [CHARACTER SET charset_name] [COLLATE collation_name]
    testText TEXT
    -- BINARY[(M)]
    -- testBinary BINARY,
    -- VARBINARY(M)
    -- testVarBinary VARBINARY,
    -- TINYBLOB
    -- testTinyBlob TINYBLOB,
    -- BLOB[(M)]
    -- testBlob BLOB,
    -- MEDIUMBLOB
    -- testMediumBlob MEDIUMBLOB
    -- LONGBLOB
    -- testLongBlob LONGBLOB,

    -- ENUM('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]
    -- SET('value1','value2',...) [CHARACTER SET charset_name] [COLLATE collation_name]
);

INSERT INTO Test(`tests`.`Test`.`testText`) VALUES ('HELLO');
