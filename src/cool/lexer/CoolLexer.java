// Generated from CoolLexer.g4 by ANTLR 4.7.2

    package cool.lexer;	

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CoolLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ERROR=1, ATRIB=2, LE=3, ARROW=4, PLUS=5, MINUS=6, LT=7, EQ=8, SEMICOLON=9, 
		COLON=10, LPAR=11, RPAR=12, LCURL=13, RCURL=14, STAR=15, AT=16, COMMA=17, 
		SLASH=18, TILDE=19, DOT=20, IF=21, ELSE=22, FI=23, THEN=24, CLASS=25, 
		IN=26, INHERITS=27, ISVOID=28, LET=29, LOOP=30, POOL=31, WHILE=32, CASE=33, 
		ESAC=34, NEW=35, OF=36, NOT=37, BOOL=38, INT=39, TYPE=40, ID=41, WS=42, 
		SINGLE_LINE_COMMENT=43, INVALID_COMMENT=44, INVALID_CHAR=45, EOF_COMMENT=46, 
		END_COMMENT=47, SNL=48, STRING_LITERAL=49, EOF_STRING=50, EOF_C=51, BEGIN_COMMENT=52, 
		BEGIN_STRING=53, SQUOTE=54;
	public static final int
		COMMENT=1, STRING=2, CINC=3;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "COMMENT", "STRING", "CINC"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ATRIB", "LE", "ARROW", "PLUS", "MINUS", "LT", "EQ", "SEMICOLON", "COLON", 
			"LPAR", "RPAR", "LCURL", "RCURL", "STAR", "AT", "COMMA", "SLASH", "TILDE", 
			"DOT", "A", "C", "D", "E", "F", "H", "I", "L", "N", "O", "P", "Q", "R", 
			"S", "T", "U", "V", "W", "IF", "ELSE", "FI", "THEN", "CLASS", "IN", "INHERITS", 
			"ISVOID", "LET", "LOOP", "POOL", "WHILE", "CASE", "ESAC", "NEW", "OF", 
			"NOT", "TRUE", "FALSE", "LLETTER", "ULETTER", "DIGIT", "BOOL", "INT", 
			"TYPE", "ID", "WS", "SINGLE_LINE_COMMENT", "INVALID_COMMENT", "BEGIN_COMMENT", 
			"BEGIN_STRING", "INVALID_CHAR", "COMMENT_IN_COMMENT", "EOF_COMMENT", 
			"END_COMMENT", "CONTENT", "SQUOTE", "SNL_ESC", "SNL", "STRING_LITERAL", 
			"EOF_STRING", "SCHAR", "C_IN_C", "EOF_C", "END_C", "CCC"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, "'<-'", "'<='", "'=>'", "'+'", "'-'", "'<'", "'='", "';'", 
			"':'", "'('", "')'", "'{'", "'}'", "'*'", "'@'", "','", "'/'", "'~'", 
			"'.'", null, null, null, null, "'class'", null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "'\"'", "'\\\"'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ERROR", "ATRIB", "LE", "ARROW", "PLUS", "MINUS", "LT", "EQ", "SEMICOLON", 
			"COLON", "LPAR", "RPAR", "LCURL", "RCURL", "STAR", "AT", "COMMA", "SLASH", 
			"TILDE", "DOT", "IF", "ELSE", "FI", "THEN", "CLASS", "IN", "INHERITS", 
			"ISVOID", "LET", "LOOP", "POOL", "WHILE", "CASE", "ESAC", "NEW", "OF", 
			"NOT", "BOOL", "INT", "TYPE", "ID", "WS", "SINGLE_LINE_COMMENT", "INVALID_COMMENT", 
			"INVALID_CHAR", "EOF_COMMENT", "END_COMMENT", "SNL", "STRING_LITERAL", 
			"EOF_STRING", "EOF_C", "BEGIN_COMMENT", "BEGIN_STRING", "SQUOTE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	    
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


	public CoolLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CoolLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 65:
			INVALID_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 68:
			INVALID_CHAR_action((RuleContext)_localctx, actionIndex);
			break;
		case 70:
			EOF_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 75:
			SNL_action((RuleContext)_localctx, actionIndex);
			break;
		case 76:
			STRING_LITERAL_action((RuleContext)_localctx, actionIndex);
			break;
		case 77:
			EOF_STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 80:
			EOF_C_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void INVALID_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			raiseError("Unmatched *)");
			break;
		}
	}
	private void INVALID_CHAR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			raiseError("Invalid character: " + getText());
			break;
		}
	}
	private void EOF_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			raiseError("EOF in comment");
			break;
		}
	}
	private void SNL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:
			raiseError("Unterminated string constant");
			break;
		}
	}
	private void STRING_LITERAL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			formatString();
			break;
		}
	}
	private void EOF_STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			raiseError("EOF in string constant");
			break;
		}
	}
	private void EOF_C_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			raiseError("EOF in comment");
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\28\u01f8\b\1\b\1\b"+
		"\1\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t"+
		"\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21"+
		"\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30"+
		"\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37"+
		"\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)"+
		"\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63"+
		"\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;"+
		"\4<\t<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G"+
		"\tG\4H\tH\4I\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR"+
		"\4S\tS\4T\tT\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3"+
		"\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3"+
		"\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3"+
		"\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3"+
		"\'\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3"+
		",\3,\3,\3-\3-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3"+
		"\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3"+
		"\62\3\62\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3"+
		"\65\3\65\3\66\3\66\3\66\3\67\3\67\3\67\3\67\38\38\38\38\38\39\39\39\3"+
		"9\39\39\3:\3:\3;\3;\3<\3<\3=\3=\5=\u015f\n=\3>\6>\u0162\n>\r>\16>\u0163"+
		"\3?\6?\u0167\n?\r?\16?\u0168\3?\3?\3?\3?\7?\u016f\n?\f?\16?\u0172\13?"+
		"\3@\6@\u0175\n@\r@\16@\u0176\3@\3@\3@\3@\7@\u017d\n@\f@\16@\u0180\13@"+
		"\3A\6A\u0183\nA\rA\16A\u0184\3A\3A\3B\3B\3B\3B\7B\u018d\nB\fB\16B\u0190"+
		"\13B\3B\3B\3B\5B\u0195\nB\3B\3B\3C\3C\3C\3C\3C\3D\3D\3D\3D\3D\3D\3E\3"+
		"E\3E\3E\3E\3F\3F\3F\3G\3G\3G\3G\3G\3G\3H\3H\3H\3H\3H\3H\3I\3I\3I\3I\3"+
		"I\3I\3J\3J\3J\3J\3K\3K\3K\3K\3K\3L\3L\3L\3L\5L\u01cb\nL\3L\3L\3M\3M\3"+
		"M\3M\3M\3N\3N\3N\3N\3N\3O\3O\3O\3O\3O\3O\3P\3P\3P\3P\3Q\3Q\3Q\3Q\3Q\3"+
		"Q\3R\3R\3R\3R\3R\3R\3S\3S\3S\3S\3S\3S\3T\3T\3T\3T\3\u018e\2U\6\4\b\5\n"+
		"\6\f\7\16\b\20\t\22\n\24\13\26\f\30\r\32\16\34\17\36\20 \21\"\22$\23&"+
		"\24(\25*\26,\2.\2\60\2\62\2\64\2\66\28\2:\2<\2>\2@\2B\2D\2F\2H\2J\2L\2"+
		"N\2P\27R\30T\31V\32X\33Z\34\\\35^\36`\37b d!f\"h#j$l%n&p\'r\2t\2v\2x\2"+
		"z\2|(~)\u0080*\u0082+\u0084,\u0086-\u0088.\u008a\66\u008c\67\u008e/\u0090"+
		"\2\u0092\60\u0094\61\u0096\2\u00988\u009a\2\u009c\62\u009e\63\u00a0\64"+
		"\u00a2\2\u00a4\2\u00a6\65\u00a8\2\u00aa\2\6\2\3\4\5\31\4\2CCcc\4\2EEe"+
		"e\4\2FFff\4\2GGgg\4\2HHhh\4\2JJjj\4\2KKkk\4\2NNnn\4\2PPpp\4\2QQqq\4\2"+
		"RRrr\4\2SSss\4\2TTtt\4\2UUuu\4\2VVvv\4\2WWww\4\2XXxx\4\2YYyy\3\2c|\3\2"+
		"C\\\3\2\62;\5\2\13\f\16\17\"\"\4\2\f\f\17\17\2\u01ed\2\6\3\2\2\2\2\b\3"+
		"\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2\2\2\2\20\3\2\2\2\2\22\3\2\2\2"+
		"\2\24\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2\2\32\3\2\2\2\2\34\3\2\2\2\2\36"+
		"\3\2\2\2\2 \3\2\2\2\2\"\3\2\2\2\2$\3\2\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3"+
		"\2\2\2\2P\3\2\2\2\2R\3\2\2\2\2T\3\2\2\2\2V\3\2\2\2\2X\3\2\2\2\2Z\3\2\2"+
		"\2\2\\\3\2\2\2\2^\3\2\2\2\2`\3\2\2\2\2b\3\2\2\2\2d\3\2\2\2\2f\3\2\2\2"+
		"\2h\3\2\2\2\2j\3\2\2\2\2l\3\2\2\2\2n\3\2\2\2\2p\3\2\2\2\2|\3\2\2\2\2~"+
		"\3\2\2\2\2\u0080\3\2\2\2\2\u0082\3\2\2\2\2\u0084\3\2\2\2\2\u0086\3\2\2"+
		"\2\2\u0088\3\2\2\2\2\u008a\3\2\2\2\2\u008c\3\2\2\2\2\u008e\3\2\2\2\3\u0090"+
		"\3\2\2\2\3\u0092\3\2\2\2\3\u0094\3\2\2\2\3\u0096\3\2\2\2\4\u0098\3\2\2"+
		"\2\4\u009a\3\2\2\2\4\u009c\3\2\2\2\4\u009e\3\2\2\2\4\u00a0\3\2\2\2\4\u00a2"+
		"\3\2\2\2\5\u00a4\3\2\2\2\5\u00a6\3\2\2\2\5\u00a8\3\2\2\2\5\u00aa\3\2\2"+
		"\2\6\u00ac\3\2\2\2\b\u00af\3\2\2\2\n\u00b2\3\2\2\2\f\u00b5\3\2\2\2\16"+
		"\u00b7\3\2\2\2\20\u00b9\3\2\2\2\22\u00bb\3\2\2\2\24\u00bd\3\2\2\2\26\u00bf"+
		"\3\2\2\2\30\u00c1\3\2\2\2\32\u00c3\3\2\2\2\34\u00c5\3\2\2\2\36\u00c7\3"+
		"\2\2\2 \u00c9\3\2\2\2\"\u00cb\3\2\2\2$\u00cd\3\2\2\2&\u00cf\3\2\2\2(\u00d1"+
		"\3\2\2\2*\u00d3\3\2\2\2,\u00d5\3\2\2\2.\u00d7\3\2\2\2\60\u00d9\3\2\2\2"+
		"\62\u00db\3\2\2\2\64\u00dd\3\2\2\2\66\u00df\3\2\2\28\u00e1\3\2\2\2:\u00e3"+
		"\3\2\2\2<\u00e5\3\2\2\2>\u00e7\3\2\2\2@\u00e9\3\2\2\2B\u00eb\3\2\2\2D"+
		"\u00ed\3\2\2\2F\u00ef\3\2\2\2H\u00f1\3\2\2\2J\u00f3\3\2\2\2L\u00f5\3\2"+
		"\2\2N\u00f7\3\2\2\2P\u00f9\3\2\2\2R\u00fc\3\2\2\2T\u0101\3\2\2\2V\u0104"+
		"\3\2\2\2X\u0109\3\2\2\2Z\u010f\3\2\2\2\\\u0112\3\2\2\2^\u011b\3\2\2\2"+
		"`\u0122\3\2\2\2b\u0126\3\2\2\2d\u012b\3\2\2\2f\u0130\3\2\2\2h\u0136\3"+
		"\2\2\2j\u013b\3\2\2\2l\u0140\3\2\2\2n\u0144\3\2\2\2p\u0147\3\2\2\2r\u014b"+
		"\3\2\2\2t\u0150\3\2\2\2v\u0156\3\2\2\2x\u0158\3\2\2\2z\u015a\3\2\2\2|"+
		"\u015e\3\2\2\2~\u0161\3\2\2\2\u0080\u0166\3\2\2\2\u0082\u0174\3\2\2\2"+
		"\u0084\u0182\3\2\2\2\u0086\u0188\3\2\2\2\u0088\u0198\3\2\2\2\u008a\u019d"+
		"\3\2\2\2\u008c\u01a3\3\2\2\2\u008e\u01a8\3\2\2\2\u0090\u01ab\3\2\2\2\u0092"+
		"\u01b1\3\2\2\2\u0094\u01b7\3\2\2\2\u0096\u01bd\3\2\2\2\u0098\u01c1\3\2"+
		"\2\2\u009a\u01c6\3\2\2\2\u009c\u01ce\3\2\2\2\u009e\u01d3\3\2\2\2\u00a0"+
		"\u01d8\3\2\2\2\u00a2\u01de\3\2\2\2\u00a4\u01e2\3\2\2\2\u00a6\u01e8\3\2"+
		"\2\2\u00a8\u01ee\3\2\2\2\u00aa\u01f4\3\2\2\2\u00ac\u00ad\7>\2\2\u00ad"+
		"\u00ae\7/\2\2\u00ae\7\3\2\2\2\u00af\u00b0\7>\2\2\u00b0\u00b1\7?\2\2\u00b1"+
		"\t\3\2\2\2\u00b2\u00b3\7?\2\2\u00b3\u00b4\7@\2\2\u00b4\13\3\2\2\2\u00b5"+
		"\u00b6\7-\2\2\u00b6\r\3\2\2\2\u00b7\u00b8\7/\2\2\u00b8\17\3\2\2\2\u00b9"+
		"\u00ba\7>\2\2\u00ba\21\3\2\2\2\u00bb\u00bc\7?\2\2\u00bc\23\3\2\2\2\u00bd"+
		"\u00be\7=\2\2\u00be\25\3\2\2\2\u00bf\u00c0\7<\2\2\u00c0\27\3\2\2\2\u00c1"+
		"\u00c2\7*\2\2\u00c2\31\3\2\2\2\u00c3\u00c4\7+\2\2\u00c4\33\3\2\2\2\u00c5"+
		"\u00c6\7}\2\2\u00c6\35\3\2\2\2\u00c7\u00c8\7\177\2\2\u00c8\37\3\2\2\2"+
		"\u00c9\u00ca\7,\2\2\u00ca!\3\2\2\2\u00cb\u00cc\7B\2\2\u00cc#\3\2\2\2\u00cd"+
		"\u00ce\7.\2\2\u00ce%\3\2\2\2\u00cf\u00d0\7\61\2\2\u00d0\'\3\2\2\2\u00d1"+
		"\u00d2\7\u0080\2\2\u00d2)\3\2\2\2\u00d3\u00d4\7\60\2\2\u00d4+\3\2\2\2"+
		"\u00d5\u00d6\t\2\2\2\u00d6-\3\2\2\2\u00d7\u00d8\t\3\2\2\u00d8/\3\2\2\2"+
		"\u00d9\u00da\t\4\2\2\u00da\61\3\2\2\2\u00db\u00dc\t\5\2\2\u00dc\63\3\2"+
		"\2\2\u00dd\u00de\t\6\2\2\u00de\65\3\2\2\2\u00df\u00e0\t\7\2\2\u00e0\67"+
		"\3\2\2\2\u00e1\u00e2\t\b\2\2\u00e29\3\2\2\2\u00e3\u00e4\t\t\2\2\u00e4"+
		";\3\2\2\2\u00e5\u00e6\t\n\2\2\u00e6=\3\2\2\2\u00e7\u00e8\t\13\2\2\u00e8"+
		"?\3\2\2\2\u00e9\u00ea\t\f\2\2\u00eaA\3\2\2\2\u00eb\u00ec\t\r\2\2\u00ec"+
		"C\3\2\2\2\u00ed\u00ee\t\16\2\2\u00eeE\3\2\2\2\u00ef\u00f0\t\17\2\2\u00f0"+
		"G\3\2\2\2\u00f1\u00f2\t\20\2\2\u00f2I\3\2\2\2\u00f3\u00f4\t\21\2\2\u00f4"+
		"K\3\2\2\2\u00f5\u00f6\t\22\2\2\u00f6M\3\2\2\2\u00f7\u00f8\t\23\2\2\u00f8"+
		"O\3\2\2\2\u00f9\u00fa\58\33\2\u00fa\u00fb\5\64\31\2\u00fbQ\3\2\2\2\u00fc"+
		"\u00fd\5\62\30\2\u00fd\u00fe\5:\34\2\u00fe\u00ff\5F\"\2\u00ff\u0100\5"+
		"\62\30\2\u0100S\3\2\2\2\u0101\u0102\5\64\31\2\u0102\u0103\58\33\2\u0103"+
		"U\3\2\2\2\u0104\u0105\5H#\2\u0105\u0106\5\66\32\2\u0106\u0107\5\62\30"+
		"\2\u0107\u0108\5<\35\2\u0108W\3\2\2\2\u0109\u010a\7e\2\2\u010a\u010b\7"+
		"n\2\2\u010b\u010c\7c\2\2\u010c\u010d\7u\2\2\u010d\u010e\7u\2\2\u010eY"+
		"\3\2\2\2\u010f\u0110\58\33\2\u0110\u0111\5<\35\2\u0111[\3\2\2\2\u0112"+
		"\u0113\58\33\2\u0113\u0114\5<\35\2\u0114\u0115\5\66\32\2\u0115\u0116\5"+
		"\62\30\2\u0116\u0117\5D!\2\u0117\u0118\58\33\2\u0118\u0119\5H#\2\u0119"+
		"\u011a\5F\"\2\u011a]\3\2\2\2\u011b\u011c\58\33\2\u011c\u011d\5F\"\2\u011d"+
		"\u011e\5L%\2\u011e\u011f\5>\36\2\u011f\u0120\58\33\2\u0120\u0121\5\60"+
		"\27\2\u0121_\3\2\2\2\u0122\u0123\5:\34\2\u0123\u0124\5\62\30\2\u0124\u0125"+
		"\5H#\2\u0125a\3\2\2\2\u0126\u0127\5:\34\2\u0127\u0128\5>\36\2\u0128\u0129"+
		"\5>\36\2\u0129\u012a\5@\37\2\u012ac\3\2\2\2\u012b\u012c\5@\37\2\u012c"+
		"\u012d\5>\36\2\u012d\u012e\5>\36\2\u012e\u012f\5:\34\2\u012fe\3\2\2\2"+
		"\u0130\u0131\5N&\2\u0131\u0132\5\66\32\2\u0132\u0133\58\33\2\u0133\u0134"+
		"\5:\34\2\u0134\u0135\5\62\30\2\u0135g\3\2\2\2\u0136\u0137\5.\26\2\u0137"+
		"\u0138\5,\25\2\u0138\u0139\5F\"\2\u0139\u013a\5\62\30\2\u013ai\3\2\2\2"+
		"\u013b\u013c\5\62\30\2\u013c\u013d\5F\"\2\u013d\u013e\5,\25\2\u013e\u013f"+
		"\5.\26\2\u013fk\3\2\2\2\u0140\u0141\5<\35\2\u0141\u0142\5\62\30\2\u0142"+
		"\u0143\5N&\2\u0143m\3\2\2\2\u0144\u0145\5>\36\2\u0145\u0146\5\64\31\2"+
		"\u0146o\3\2\2\2\u0147\u0148\5<\35\2\u0148\u0149\5>\36\2\u0149\u014a\5"+
		"H#\2\u014aq\3\2\2\2\u014b\u014c\7v\2\2\u014c\u014d\5D!\2\u014d\u014e\5"+
		"J$\2\u014e\u014f\5\62\30\2\u014fs\3\2\2\2\u0150\u0151\7h\2\2\u0151\u0152"+
		"\5,\25\2\u0152\u0153\5:\34\2\u0153\u0154\5F\"\2\u0154\u0155\5\62\30\2"+
		"\u0155u\3\2\2\2\u0156\u0157\t\24\2\2\u0157w\3\2\2\2\u0158\u0159\t\25\2"+
		"\2\u0159y\3\2\2\2\u015a\u015b\t\26\2\2\u015b{\3\2\2\2\u015c\u015f\5r8"+
		"\2\u015d\u015f\5t9\2\u015e\u015c\3\2\2\2\u015e\u015d\3\2\2\2\u015f}\3"+
		"\2\2\2\u0160\u0162\5z<\2\u0161\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163"+
		"\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164\177\3\2\2\2\u0165\u0167\5x;\2"+
		"\u0166\u0165\3\2\2\2\u0167\u0168\3\2\2\2\u0168\u0166\3\2\2\2\u0168\u0169"+
		"\3\2\2\2\u0169\u0170\3\2\2\2\u016a\u016f\5v:\2\u016b\u016f\5x;\2\u016c"+
		"\u016f\5z<\2\u016d\u016f\7a\2\2\u016e\u016a\3\2\2\2\u016e\u016b\3\2\2"+
		"\2\u016e\u016c\3\2\2\2\u016e\u016d\3\2\2\2\u016f\u0172\3\2\2\2\u0170\u016e"+
		"\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0081\3\2\2\2\u0172\u0170\3\2\2\2\u0173"+
		"\u0175\5v:\2\u0174\u0173\3\2\2\2\u0175\u0176\3\2\2\2\u0176\u0174\3\2\2"+
		"\2\u0176\u0177\3\2\2\2\u0177\u017e\3\2\2\2\u0178\u017d\5v:\2\u0179\u017d"+
		"\5x;\2\u017a\u017d\5z<\2\u017b\u017d\7a\2\2\u017c\u0178\3\2\2\2\u017c"+
		"\u0179\3\2\2\2\u017c\u017a\3\2\2\2\u017c\u017b\3\2\2\2\u017d\u0180\3\2"+
		"\2\2\u017e\u017c\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0083\3\2\2\2\u0180"+
		"\u017e\3\2\2\2\u0181\u0183\t\27\2\2\u0182\u0181\3\2\2\2\u0183\u0184\3"+
		"\2\2\2\u0184\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185\u0186\3\2\2\2\u0186"+
		"\u0187\bA\2\2\u0187\u0085\3\2\2\2\u0188\u0189\7/\2\2\u0189\u018a\7/\2"+
		"\2\u018a\u018e\3\2\2\2\u018b\u018d\13\2\2\2\u018c\u018b\3\2\2\2\u018d"+
		"\u0190\3\2\2\2\u018e\u018f\3\2\2\2\u018e\u018c\3\2\2\2\u018f\u0194\3\2"+
		"\2\2\u0190\u018e\3\2\2\2\u0191\u0195\7\f\2\2\u0192\u0193\7\17\2\2\u0193"+
		"\u0195\7\f\2\2\u0194\u0191\3\2\2\2\u0194\u0192\3\2\2\2\u0195\u0196\3\2"+
		"\2\2\u0196\u0197\bB\2\2\u0197\u0087\3\2\2\2\u0198\u0199\7,\2\2\u0199\u019a"+
		"\7+\2\2\u019a\u019b\3\2\2\2\u019b\u019c\bC\3\2\u019c\u0089\3\2\2\2\u019d"+
		"\u019e\7*\2\2\u019e\u019f\7,\2\2\u019f\u01a0\3\2\2\2\u01a0\u01a1\bD\4"+
		"\2\u01a1\u01a2\bD\5\2\u01a2\u008b\3\2\2\2\u01a3\u01a4\7$\2\2\u01a4\u01a5"+
		"\3\2\2\2\u01a5\u01a6\bE\6\2\u01a6\u01a7\bE\5\2\u01a7\u008d\3\2\2\2\u01a8"+
		"\u01a9\13\2\2\2\u01a9\u01aa\bF\7\2\u01aa\u008f\3\2\2\2\u01ab\u01ac\7*"+
		"\2\2\u01ac\u01ad\7,\2\2\u01ad\u01ae\3\2\2\2\u01ae\u01af\bG\b\2\u01af\u01b0"+
		"\bG\5\2\u01b0\u0091\3\2\2\2\u01b1\u01b2\13\2\2\2\u01b2\u01b3\7\2\2\3\u01b3"+
		"\u01b4\bH\t\2\u01b4\u01b5\3\2\2\2\u01b5\u01b6\bH\n\2\u01b6\u0093\3\2\2"+
		"\2\u01b7\u01b8\7,\2\2\u01b8\u01b9\7+\2\2\u01b9\u01ba\3\2\2\2\u01ba\u01bb"+
		"\bI\n\2\u01bb\u01bc\bI\2\2\u01bc\u0095\3\2\2\2\u01bd\u01be\13\2\2\2\u01be"+
		"\u01bf\3\2\2\2\u01bf\u01c0\bJ\5\2\u01c0\u0097\3\2\2\2\u01c1\u01c2\7^\2"+
		"\2\u01c2\u01c3\7$\2\2\u01c3\u01c4\3\2\2\2\u01c4\u01c5\bK\5\2\u01c5\u0099"+
		"\3\2\2\2\u01c6\u01ca\7^\2\2\u01c7\u01cb\7\f\2\2\u01c8\u01c9\7\17\2\2\u01c9"+
		"\u01cb\7\f\2\2\u01ca\u01c7\3\2\2\2\u01ca\u01c8\3\2\2\2\u01cb\u01cc\3\2"+
		"\2\2\u01cc\u01cd\bL\5\2\u01cd\u009b\3\2\2\2\u01ce\u01cf\t\30\2\2\u01cf"+
		"\u01d0\bM\13\2\u01d0\u01d1\3\2\2\2\u01d1\u01d2\bM\n\2\u01d2\u009d\3\2"+
		"\2\2\u01d3\u01d4\7$\2\2\u01d4\u01d5\bN\f\2\u01d5\u01d6\3\2\2\2\u01d6\u01d7"+
		"\bN\n\2\u01d7\u009f\3\2\2\2\u01d8\u01d9\13\2\2\2\u01d9\u01da\7\2\2\3\u01da"+
		"\u01db\bO\r\2\u01db\u01dc\3\2\2\2\u01dc\u01dd\bO\n\2\u01dd\u00a1\3\2\2"+
		"\2\u01de\u01df\13\2\2\2\u01df\u01e0\3\2\2\2\u01e0\u01e1\bP\5\2\u01e1\u00a3"+
		"\3\2\2\2\u01e2\u01e3\7*\2\2\u01e3\u01e4\7,\2\2\u01e4\u01e5\3\2\2\2\u01e5"+
		"\u01e6\bQ\b\2\u01e6\u01e7\bQ\5\2\u01e7\u00a5\3\2\2\2\u01e8\u01e9\13\2"+
		"\2\2\u01e9\u01ea\7\2\2\3\u01ea\u01eb\bR\16\2\u01eb\u01ec\3\2\2\2\u01ec"+
		"\u01ed\bR\n\2\u01ed\u00a7\3\2\2\2\u01ee\u01ef\7,\2\2\u01ef\u01f0\7+\2"+
		"\2\u01f0\u01f1\3\2\2\2\u01f1\u01f2\bS\n\2\u01f2\u01f3\bS\5\2\u01f3\u00a9"+
		"\3\2\2\2\u01f4\u01f5\13\2\2\2\u01f5\u01f6\3\2\2\2\u01f6\u01f7\bT\5\2\u01f7"+
		"\u00ab\3\2\2\2\22\2\3\4\5\u015e\u0163\u0168\u016e\u0170\u0176\u017c\u017e"+
		"\u0184\u018e\u0194\u01ca\17\b\2\2\3C\2\7\3\2\5\2\2\7\4\2\3F\3\7\5\2\3"+
		"H\4\6\2\2\3M\5\3N\6\3O\7\3R\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}