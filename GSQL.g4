grammar GSQL;

//Palabras Reservadas

INT : 'INT' | 'Int' | 'int';
CHAR : 'CHAR' | 'Char' | 'char';
FLOAT : 'FLOAT' | 'Float' | 'float';
DATE : 'DATE' | 'Date' | 'date';
CREATE : 'CREATE' | 'Create' | 'create';
DATABASE : 'DATABASE' | 'Database' | 'database';
DATABASES : 'DATABASES' | 'Databases' | 'databases';
ALTER : 'ALTER' | 'Alter' | 'alter';
RENAME : 'RENAME' | 'Rename' | 'rename';
TO : 'TO' | 'To' | 'to';
DROP : 'DROP' | 'Drop' | 'drop';
USE : 'USE' | 'Use' | 'use';
SHOW : 'SHOW' | 'Show' | 'show';
TABLE : 'TABLE' | 'Table' | 'table';
TABLES : 'TABLES' | 'Tables' | 'tables';
PRIMARY : 'PRIMARY' | 'Primary' | 'primary';
KEY : 'KEY' | 'Key' | 'key';
FOREIGN : 'FOREIGN' | 'Foreign' | 'foreign';
CHECK : 'CHECK' | 'Check' | 'check';
REFERENCES : 'REFERENCES' | 'References' | 'references';
NOT : 'NOT' | 'Not' | 'not';
OR : 'OR' | 'Or' | 'or';
AND : 'AND' | 'And' | 'and';
ADD : 'ADD' | 'Add' | 'add';
COLUMN : 'COLUMN' | 'Column' | 'column';
COLUMNS : 'COLUMNS' | 'Columns' | 'columns';
CONSTRAINT : 'CONSTRAINT' | 'Constraint' | 'constraint';
FROM : 'FROM' | 'From' | 'from';
INSERT : 'INSERT' | 'Insert' | 'insert';
INTO : 'INTO' | 'Into' | 'into';
VALUES : 'VALUES' | 'Values' | 'values';
UPDATE : 'UPDATE' | 'Update' | 'update';
SET : 'SET' | 'Set' | 'set';
WHERE : 'WHERE' | 'Where' | 'where';
DELETE : 'DELETE' | 'Delete' | 'delete';
SELECT : 'SELECT' | 'Select' | 'select';
ORDER : 'ORDER' | 'Order' | 'order';
BY : 'BY' | 'By' | 'by';
ASC : 'ASC' | 'Asc' | 'asc';
DESC : 'DESC' | 'Desc' | 'desc';

fragment Letter : ('a'..'z'|'A'..'Z') ;
fragment Digit :'0'..'9' ;
fragment Any : ' ' | '#' | '$' | '%' | '.' | '_' | '\\' | '\'' | '"' | '\t' | '\n' ;
fragment AnyT : (' ' .. '~') | '\\' | '\'' | '"' | '\t' | '\n' ;
fragment AnyAll : Letter | Digit | Any ;
fragment DateDay : ('0' | '1' | '2' | '3') Digit;
fragment DateMonth: ('0'|'1') Digit;
fragment DateYear : ('1'|'2') Digit Digit Digit;

Date : DateYear '-' DateMonth '-' DateDay;
Id : Letter(Letter|Digit|'_')* ;
Num : ('-')? Digit(Digit)* ;
Float : Digit(Digit)* '.' Digit(Digit)*;
Char : '\'' (AnyAll)* '\'' ;
Comments: ('//' ~('\r' | '\n' )* | '/*' AnyAll* '*/') -> channel(HIDDEN);
WhitespaceDeclaration : [\t\r\n\f ]+ -> skip ;

program
	:	database (database)* 
	;

database
	:	( createDatabase
	|	alterDatabase
	|	dropDatabase
	|	useDatabase
	|	showDatabase
	|	tableInstruction ) ';'
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
	|	select )
	;
		
createTable
	:	CREATE TABLE Id ( '(' Id type (',' Id type)* (',' CONSTRAINT constraint (',' constraint)*) ')' )?
	;

constraint
	:	primaryKey | foreignKey (foreignKey)* | check
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
	:	(NOT)? selectLiteral											
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
	
selectLiteral
	:	select_int
	|	select_char
	|	select_float
	|	select_date
	|	select_id
	;

select_int
	:	Num
	;

select_float
	:	Float
	;
	
select_char
	:	Char
	;

select_date
	: 	Date
	;

select_id
	:	Id									#simpId_literal
	|	Id '.' Id							#doubleId_literal
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
	:	UPDATE Id SET (Id '=' literal)(',' Id '=' literal)* (where)?
	;

deleteFrom
	:	DELETE FROM Id (where)?
	;
	
select
	:	SELECT (all='*'|select_id (',' select_id)*) from ( where )? ( orderBy )?  
	;
	
from
	:	FROM Id (',' Id)*
	;

where	
	:	WHERE expression
	;
	
orderBy	
	:	ORDER BY orderId (',' orderId)*
	;
	
orderId
	:	select_id (orderType)?	
	;

orderType
	:	(ASC|DESC)
	;
