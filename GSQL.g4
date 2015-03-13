grammar GSQL;

//Palabras Reservadas

INT : 'int' ;
CHAR : 'char' ;
FLOAT : 'float';
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


fragment Letter : ('a'..'z'|'A'..'Z') ;
fragment Digit :'0'..'9' ;
fragment Any : (' ' ..'~') | '\\' | '\'' | '"' | '\t' | '\n' ;
fragment AnyAll : Letter | Digit | Any ;

Id : Letter(Letter|Digit)* ;
Num : Digit(Digit)* ;
Char : '\'' (AnyAll)* '\'' ;
Comments: '//' ~('\r' | '\n' )*  -> channel(HIDDEN);
WhitespaceDeclaration : [\t\r\n\f ]+ -> skip ;

database
	:	createDatabase
	|	alterDatabase
	|	dropDatabase
	|	useDatabase
	|	showDatabase
	|	tableInstruction
	;

createDatabase
	:	CREATE DATABASE Id ';'
	;

alterDatabase
	:	ALTER DATABASE Id RENAME TO Id ';'
	;
	
dropDatabase
	:	DROP DATABASE Id ';'
	;
	
useDatabase
	:	USE DATABASE Id ';'
	;
	
showDatabase
	:	SHOW DATABASES ';'
	;
	
tableInstruction
	:	(createTable
	|	alterTable
	|	dropTable
	|	showTables
	|	showColumns) ';'
	
	//|	insertInto
	//|	updateSet
	//|	deleteFrom
	//|	selectFrom
	;
		
createTable
	:	CREATE TABLE Id '(' (Id type (',' Id type)*)? (constraint)?  ')' ';'
	;

constraint
	:	(Id PRIMARY KEY '(' Id (',' Id)* ')'
	|	Id FOREIGN KEY '(' Id (',' Id)* ')' REFERENCES Id '(' Id (',' Id)* ')')
		(Id CHECK '(' expression ')')*
	;
	
type
	:	INT
	|	FLOAT
	|	date
	|	CHAR '(' Num ')'
	;

date
	: ('1'|'2') Digit Digit Digit '-' ('0'|'1') Digit '-' ('0'|'1'|'2'|'3') Digit ;
	
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
	:	ALTER TABLE Id RENAME TO Id
	|	ALTER TABLE Id (action)*
	;

action
	:	ADD COLUMN Id type CONSTRAINT constraint
	|	ADD CONSTRAINT constraint
	|	DROP COLUMN Id
	|	DROP CONSTRAINT Id
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
	;
	
int_literal
	:	Num
	;

float_literal
	:	Num '.' Num
	|	Num '/' Num
	;
	
char_literal
	:	Char
	;
	


