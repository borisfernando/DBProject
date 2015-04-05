grammar GSQL;

//Palabras Reservadas

INT : 'int';
CHAR : 'char';
FLOAT : 'float';
DATE : 'date';
CREATE : 'CREATE';
DATABASE : 'DATABASE';
DATABASES : 'DATABASES';
ALTER : 'ALTER';
RENAME : 'RENAME';
TO : 'TO';
DROP : 'DROP';
USE : 'USE';
SHOW : 'SHOW';
TABLE : 'TABLE';
TABLES : 'TABLES';
PRIMARY : 'PRIMARY';
KEY : 'KEY';
FOREIGN : 'FOREIGN';
CHECK : 'CHECK';
REFERENCES : 'REFERENCES';
NOT : 'NOT';
OR : 'OR';
AND : 'AND';
ADD : 'ADD';
COLUMN : 'COLUMN';
COLUMNS : 'COLUMNS';
CONSTRAINT : 'CONSTRAINT';
FROM : 'FROM';
INSERT : 'INSERT';
INTO : 'INTO';
VALUES : 'VALUES';
UPDATE : 'UPDATE';
SET : 'SET';
WHERE : 'WHERE';
DELETE : 'DELETE';
SELECT : 'SELECT';
ORDER : 'ORDER';
BY : 'BY';
ASC : 'ASC';
DESC : 'DESC';

fragment Letter : ('a'..'z'|'A'..'Z') ;
fragment Digit :'0'..'9' ;
fragment Any : ' ' | '#' | '$' | '%' | '.' | '_' | '\\' | '\'' | '"' | '\t' | '\n' ;
fragment AnyT : (' ' .. '~') | '\\' | '\'' | '"' | '\t' | '\n' ;
fragment AnyAll : Letter | Digit | Any ;
fragment DateDay : ('0' | '1' | '2' | '3') Digit;
fragment DateMonth: ('0'|'1') Digit;
fragment DateYear : ('1'|'2') Digit Digit Digit;

Id : Letter(Letter|Digit|'_')* ;
Num : Digit(Digit)* ;
Float : Digit(Digit)* '.' Digit(Digit)*;
Char : '\'' (AnyAll)* '\'' ;
Date : DateYear '-' DateMonth '-' DateDay;
Comments: '//' ~('\r' | '\n' )*  -> channel(HIDDEN);
WhitespaceDeclaration : [\t\r\n\f ]+ -> skip ;

program
	:	database ';' (database ';')* 
	;

database
	:	createDatabase
	|	alterDatabase
	|	dropDatabase
	|	useDatabase
	|	showDatabase
	|	tableInstruction
	;

createDatabase
	:	CREATE DATABASE Id 
	;

alterDatabase
	:	ALTER DATABASE Id RENAME TO Id 
	;
	
dropDatabase
	:	DROP DATABASE Id 
	;
	
useDatabase
	:	USE DATABASE Id 
	;
	
showDatabase
	:	SHOW DATABASES
	;
	
tableInstruction
	// Parte 1
	:	(createTable
	|	alterTable
	|	dropTable
	|	showTables
	|	showColumns
	
	// Parte 2
	
	|	insertInto
	|	updateSet
	|	deleteFrom
	|	selectFrom ) 
	;
		
createTable
	:	CREATE TABLE Id '(' Id type (',' Id type)* (',' CONSTRAINT (constraint (',' constraint)*))? ')'
	;

constraint
	:	primaryKey | foreignKey (foreignKey)* | check (check)*
	;
	
primaryKey
	:	Id PRIMARY KEY '(' Id (',' Id)* ')'
	;
	
foreignKey
	:	Id FOREIGN KEY '(' Id (',' Id)* ')' reference
	;
	
reference
	:	REFERENCES Id '(' Id (',' Id)* ')'
	;
	
check
	:	Id CHECK '(' expression ')'
	;
	
type
	:	INT					#intType
	|	FLOAT				#floatType
	|	DATE				#dateType
	|	CHAR '(' Num ')'	#charType
	;

expression
	:	andExpression											#expAndExpression
	|	expression orOp andExpression							#condOrExpression
	;

andExpression
	: 	eqExpression											#eqAndExpression
	|	andExpression andOp eqExpression						#condExpression
	;

eqExpression
	:	relExpression											#eqRelExpression
	|	eqExpression eqOp relExpression							#eqOpExpression
	;
	
relExpression
	:	unExpression											#relSumExpression
	| 	relExpression relOp unExpression						#relOpExpression
	;
	
unExpression
	:	(NOT)? Id
	|	literal
	;
		
eqOp
	:	'='
	|	'<>'
	;

relOp
	:	'>'
	|	'<'
	|	'>='
	|	'<='
	;

orOp
	:	OR
	;

andOp
	:	AND
	;
	
alterTable
	:	ALTER TABLE Id RENAME TO Id						#renameAlterTable
	|	ALTER TABLE Id (action)*						#actionAlterTable
	;

action
	:	ADD COLUMN Id type (',' CONSTRAINT constraint)?		#actionAddColumn
	|	ADD CONSTRAINT constraint							#actionAddConstraint
	|	DROP COLUMN Id										#actionDropColumn
	|	DROP CONSTRAINT Id									#actionDropConstraint
	;
	
dropTable
	:	DROP TABLE Id
	;

showTables
	:	SHOW TABLES
	;
	
showColumns
	:	SHOW COLUMNS FROM Id
	;
	
literal
	:	int_literal
	|	char_literal
	|	float_literal
	|	date_literal
	;
	
int_literal
	:	Num
	;

float_literal
	:	Float
	;
	
char_literal
	:	Char
	;

date_literal
	: 	Date
	;
	
insertInto	
	:	INSERT INTO Id ('('(Id(',' Id)*)? ')')? VALUES '(' (literal (',' literal)*) ')'
	;
	
updateSet
	:	UPDATE Id SET (Id '=' Char)(',' Id '=' Char)* ( WHERE expression )?
	;

deleteFrom
	:	DELETE FROM Id (WHERE expression)?
	;
	
selectFrom
	:	SELECT ('*'|Id (',' Id)*) FROM Id (',' Id)* ( WHERE expression (ORDER BY Id (ASC | DESC)? (',' Id (ASC | DESC)?)*)? )?  
	;

