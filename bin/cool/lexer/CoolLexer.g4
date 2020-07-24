lexer grammar CoolLexer;

tokens { ERROR } 

@header{
    package cool.lexer;	
}

@members{    
    private void raiseError(String msg) {
        setText(msg);
        setType(ERROR);
    }
    
    /* functie pentru 
     * 	eliminarea caracterelor pentru escapare
     * 	verificarea lungimii stringului
     * 	verificarea existentei caracaterului NULL
     */
    private void formatString() {
    	final int MAX_LEN = 1024;
    	final char ESC = '\\';
    	
    	Token t = _factory.create(_tokenFactorySourcePair, _type, _text, _channel, _tokenStartCharIndex, getCharIndex()-1, _tokenStartLine, _tokenStartCharPositionInLine);
    	String text = t.getText();
    	text = text.substring(1, text.length() -1);
    	
		char[] charsArr = text.toCharArray();
		StringBuilder goodString = new StringBuilder();
		
		for (int i = 0; i < charsArr.length; i++) {
			if (charsArr[i] == '\u0000') {
				raiseError("String contains null character");
				return;
			}
			if (charsArr[i] == ESC) {
				if (charsArr[i + 1] == 'n') {
					goodString.append('\n');
				} else if (charsArr[i + 1] == '\n') {
					goodString.append('\n');
				} else if (charsArr[i + 1] == 't') {
					goodString.append('\t');
				} else if (charsArr[i + 1] == 'b') {
					goodString.append('\b');
				} else if (charsArr[i + 1] == 'f') {
					goodString.append('\f');
				} else if (charsArr[i + 1] == '\"') {
					goodString.append('\"');
				} else if (charsArr[i + 1] == ESC) {
					goodString.append(ESC);
				} else {
					goodString.append(charsArr[i + 1]);
				}
				i++;
			} else {
				goodString.append(charsArr[i]);
			}			
		} 
		
		String newString = goodString.toString();
		if (newString.length() > MAX_LEN) {
    		raiseError("String constant too long");
			return;
    	}
    	setText(newString);
    }
}

/* Semne COOL */
ATRIB: '<-';
LE: '<=';
ARROW: '=>';
PLUS: '+';
MINUS: '-';
LT: '<';	
EQ: '=';
SEMICOLON: ';';
COLON: ':';
LPAR: '(';
RPAR: ')';
LCURL: '{';
RCURL: '}';
STAR: '*';
AT: '@';
COMMA: ',';
SLASH: '/';
TILDE: '~';
DOT: '.';

/* Fragmente folosite pentru generarea de keyworduri in COOL */
fragment A: 'a' | 'A';
fragment C: 'c' | 'C';
fragment D: 'd' | 'D';
fragment E: 'e' | 'E';
fragment F: 'f' | 'F';
fragment H: 'h' | 'H';
fragment I: 'i' | 'I';
fragment L: 'l' | 'L';
fragment N: 'n' | 'N';
fragment O: 'o' | 'O';
fragment P: 'p' | 'P';
fragment Q: 'q' | 'Q';
fragment R: 'r' | 'R';
fragment S: 's' | 'S';
fragment T: 't' | 'T';
fragment U: 'u' | 'U';
fragment V: 'v' | 'V';
fragment W: 'w' | 'W';

/* Keyworduri COOL */
IF : I F;
ELSE: E L S E;
FI: F I;
THEN: T H E N;
CLASS: 'class';
IN: I N;
INHERITS: I N H E R I T S;
ISVOID: I S V O I D;
LET: L E T;
LOOP: L O O P;
POOL: P O O L;
WHILE: W H I L E;
CASE: C A S E;
ESAC: E S A C;
NEW: N E W;
OF: O F;
NOT: N O T;

fragment TRUE: 't' R U E;
fragment FALSE: 'f' A L S E;

fragment LLETTER: [a-z];
fragment ULETTER: [A-Z];
fragment DIGIT: [0-9];

/* Tipuri de date COOL */
BOOL: TRUE | FALSE;
INT: DIGIT+;
TYPE: ULETTER+ (LLETTER | ULETTER | DIGIT | '_')*;
ID: LLETTER+ (LLETTER | ULETTER | DIGIT | '_')*;

WS
    :   [ \n\f\r\t]+ -> skip
    ;

/* Trateaza comentariile  */
SINGLE_LINE_COMMENT: '--' .*? ('\n' | '\r\n') -> skip;
INVALID_COMMENT: '*)' {raiseError("Unmatched *)");};
BEGIN_COMMENT: '(*' -> pushMode(COMMENT), more;

/* Trateaza stringurile */
BEGIN_STRING: '"' -> pushMode(STRING), more;

INVALID_CHAR: . {raiseError("Invalid character: " + getText());};

/* mod pentru blocul principal */
mode COMMENT;
COMMENT_IN_COMMENT: '(*' -> pushMode(CINC), more;
EOF_COMMENT: .EOF {raiseError("EOF in comment");} -> popMode;
END_COMMENT: '*)' -> popMode, skip;
CONTENT: . -> more;

/* mod pentru constructia stringurilor */
mode STRING; 
SQUOTE : '\\"' -> more;
SNL_ESC : '\\' ('\n' | '\r\n') -> more;
SNL : [\r\n] {raiseError("Unterminated string constant");} -> popMode;
STRING_LITERAL : '"' {formatString();} -> popMode;
EOF_STRING: .EOF {raiseError("EOF in string constant");} -> popMode;
SCHAR : . -> more;

/* mod pentru comentariile imbricate */
mode CINC;
C_IN_C: '(*' -> pushMode(CINC), more;
EOF_C: .EOF {raiseError("EOF in comment");} -> popMode;
END_C: '*)' -> popMode, more;
CCC: . -> more;
