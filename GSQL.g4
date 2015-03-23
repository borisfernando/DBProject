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

Id : Letter(Letter|Digit|'_')* ;
Num : Digit(Digit)* ;
SimpDigit : Digit;
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
	|	selectFrom ) ';'
	;
		
createTable
	:	CREATE TABLE Id '(' (Id type (',' Id type)*)? (CONSTRAINT constraint)?  ')'
	;

constraint
	:	(name=Id? PRIMARY KEY '(' Id (',' Id)* ')'
	|	name=Id? FOREIGN KEY '(' Id (',' Id)* ')' REFERENCES Id '(' Id (',' Id)* ')')
		(name=Id? CHECK '(' expression ')')*
	;
	
type
	:	INT
	|	FLOAT
	|	date
	|	CHAR '(' Num ')'
	;

date
	: ('1'|'2') SimpDigit SimpDigit SimpDigit '-' ('0'|'1') SimpDigit '-' ('0'|'1'|'2'|'3') SimpDigit 
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
	:	ADD COLUMN Id type (CONSTRAINT constraint)?		#actionAddColumn
	|	ADD CONSTRAINT constraint						#actionAddConstraint
	|	DROP COLUMN Id									#actionDropColumn
	|	DROP CONSTRAINT Id								#actionDropConstraint
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
	;
	
char_literal
	:	Char
	;
	
insertInto	
	:	INSERT INTO Id ('('(Id(',' Id)*)? ')')? VALUES '(' (literal (',' literal)*)? ')'
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

