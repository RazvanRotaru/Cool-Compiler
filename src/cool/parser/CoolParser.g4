parser grammar CoolParser;

options {
    tokenVocab = CoolLexer;
}

@header{
    package cool.parser;
}

program
    : (classDef SEMICOLON)+
    ; 
  
classDef    
	: id=CLASS self_type=TYPE (INHERITS base_type=TYPE)? LCURL (body+=feature SEMICOLON)* RCURL
	;

feature
	: id=ID COLON type=TYPE (ATRIB atrib_expr=expr)? 													# atribute
	| id=ID LPAR (args+=formal (COMMA args+=formal)*)? RPAR COLON returnType=TYPE LCURL body=expr RCURL # method
	;

formal
	: id=ID COLON type=TYPE
	;
	
caseVar
	: id=ID COLON type=TYPE ARROW expression=expr SEMICOLON
	;
	
letVar
	: id=ID COLON type=TYPE (ATRIB expression=expr)?
	;
 
expr
	: caller=expr (AT staticT=TYPE)? DOT id=ID LPAR (args+=expr (COMMA args+=expr)*)* RPAR 	# methodCall
	| id=ID LPAR (args+=expr (COMMA args+=expr)*)* RPAR 									# ownMethodCall
	| IF cond=expr THEN thenBranch=expr ELSE elseBranch=expr FI 							# if	
	| WHILE cond=expr LOOP loopBranh=expr POOL 												# while
	| LCURL (body+=expr SEMICOLON)+ RCURL 													# block
	| LET vars+=letVar (COMMA vars+=letVar)* IN body=expr 									# letIn
	| CASE var=expr OF (cases+=caseVar)+ ESAC 												# case
	| NEW type=TYPE 																		# new
	| op=TILDE expression=expr 																# negate
	| ISVOID expression=expr 																# isvoid
	| left=expr op=STAR right=expr															# multiply
	| left=expr op=SLASH right=expr 														# division
	| left=expr op=PLUS right=expr 															# plus
	| left=expr op=MINUS right=expr 														# minus
	| left=expr op=LT right=expr 															# less
	| left=expr op=LE right=expr 															# lessEq
	| left=expr op=EQ right=expr 															# equal
	| op=NOT expression=expr 																# boolNot
	| LPAR expression=expr RPAR 															# brackets
	| ID 																					# id
	| INT 																					# int
	| STRING_LITERAL 																		# string
	| BOOL 																					# bool
	| id=ID ATRIB value=expr 																# assign
	;	