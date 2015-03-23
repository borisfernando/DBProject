// Generated from GSQL.g4 by ANTLR 4.4
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GSQLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__16=1, T__15=2, T__14=3, T__13=4, T__12=5, T__11=6, T__10=7, T__9=8, 
		T__8=9, T__7=10, T__6=11, T__5=12, T__4=13, T__3=14, T__2=15, T__1=16, 
		T__0=17, INT=18, CHAR=19, FLOAT=20, CREATE=21, DATABASE=22, DATABASES=23, 
		ALTER=24, RENAME=25, TO=26, DROP=27, USE=28, SHOW=29, TABLE=30, TABLES=31, 
		PRIMARY=32, KEY=33, FOREIGN=34, CHECK=35, REFERENCES=36, NOT=37, OR=38, 
		AND=39, ADD=40, COLUMN=41, COLUMNS=42, CONSTRAINT=43, FROM=44, INSERT=45, 
		INTO=46, VALUES=47, UPDATE=48, SET=49, WHERE=50, DELETE=51, SELECT=52, 
		ORDER=53, BY=54, ASC=55, DESC=56, Id=57, Num=58, SimpDigit=59, Char=60, 
		Comments=61, WhitespaceDeclaration=62;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'", "'\\u001E'", 
		"'\\u001F'", "' '", "'!'", "'\"'", "'#'", "'$'", "'%'", "'&'", "'''", 
		"'('", "')'", "'*'", "'+'", "','", "'-'", "'.'", "'/'", "'0'", "'1'", 
		"'2'", "'3'", "'4'", "'5'", "'6'", "'7'", "'8'", "'9'", "':'", "';'", 
		"'<'", "'='", "'>'"
	};
	public static final String[] ruleNames = {
		"T__16", "T__15", "T__14", "T__13", "T__12", "T__11", "T__10", "T__9", 
		"T__8", "T__7", "T__6", "T__5", "T__4", "T__3", "T__2", "T__1", "T__0", 
		"INT", "CHAR", "FLOAT", "CREATE", "DATABASE", "DATABASES", "ALTER", "RENAME", 
		"TO", "DROP", "USE", "SHOW", "TABLE", "TABLES", "PRIMARY", "KEY", "FOREIGN", 
		"CHECK", "REFERENCES", "NOT", "OR", "AND", "ADD", "COLUMN", "COLUMNS", 
		"CONSTRAINT", "FROM", "INSERT", "INTO", "VALUES", "UPDATE", "SET", "WHERE", 
		"DELETE", "SELECT", "ORDER", "BY", "ASC", "DESC", "Letter", "Digit", "Any", 
		"AnyT", "AnyAll", "Id", "Num", "SimpDigit", "Char", "Comments", "WhitespaceDeclaration"
	};


	public GSQLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GSQL.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2@\u01d4\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\3\2\3\2\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\26\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3"+
		"\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3"+
		"\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3"+
		"\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3"+
		"!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3%\3%\3"+
		"%\3%\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3(\3)\3)\3"+
		")\3)\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3"+
		",\3,\3,\3,\3,\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3/\3/\3/\3/\3/\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\61\3\61\3\62"+
		"\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64"+
		"\3\64\3\64\3\65\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66"+
		"\3\66\3\67\3\67\3\67\38\38\38\38\39\39\39\39\39\3:\3:\3;\3;\3<\3<\3=\5"+
		"=\u01a1\n=\3>\3>\3>\5>\u01a6\n>\3?\3?\3?\3?\7?\u01ac\n?\f?\16?\u01af\13"+
		"?\3@\3@\7@\u01b3\n@\f@\16@\u01b6\13@\3A\3A\3B\3B\7B\u01bc\nB\fB\16B\u01bf"+
		"\13B\3B\3B\3C\3C\3C\3C\7C\u01c7\nC\fC\16C\u01ca\13C\3C\3C\3D\6D\u01cf"+
		"\nD\rD\16D\u01d0\3D\3D\2\2E\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a"+
		"\62c\63e\64g\65i\66k\67m8o9q:s\2u\2w\2y\2{\2};\177<\u0081=\u0083>\u0085"+
		"?\u0087@\3\2\7\4\2C\\c|\t\2\13\f\"\"$\'))\60\60^^aa\4\2\13\f\"\u0080\4"+
		"\2\f\f\17\17\5\2\13\f\16\17\"\"\u01d7\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2"+
		"\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2"+
		"[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3"+
		"\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2}\3\2\2"+
		"\2\2\177\3\2\2\2\2\u0081\3\2\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087"+
		"\3\2\2\2\3\u0089\3\2\2\2\5\u008b\3\2\2\2\7\u008d\3\2\2\2\t\u008f\3\2\2"+
		"\2\13\u0091\3\2\2\2\r\u0093\3\2\2\2\17\u0095\3\2\2\2\21\u0098\3\2\2\2"+
		"\23\u009a\3\2\2\2\25\u009c\3\2\2\2\27\u009e\3\2\2\2\31\u00a1\3\2\2\2\33"+
		"\u00a3\3\2\2\2\35\u00a5\3\2\2\2\37\u00a7\3\2\2\2!\u00a9\3\2\2\2#\u00ac"+
		"\3\2\2\2%\u00ae\3\2\2\2\'\u00b2\3\2\2\2)\u00b7\3\2\2\2+\u00bd\3\2\2\2"+
		"-\u00c4\3\2\2\2/\u00cd\3\2\2\2\61\u00d7\3\2\2\2\63\u00dd\3\2\2\2\65\u00e4"+
		"\3\2\2\2\67\u00e7\3\2\2\29\u00ec\3\2\2\2;\u00f0\3\2\2\2=\u00f5\3\2\2\2"+
		"?\u00fb\3\2\2\2A\u0102\3\2\2\2C\u010a\3\2\2\2E\u010e\3\2\2\2G\u0116\3"+
		"\2\2\2I\u011c\3\2\2\2K\u0127\3\2\2\2M\u012b\3\2\2\2O\u012e\3\2\2\2Q\u0132"+
		"\3\2\2\2S\u0136\3\2\2\2U\u013d\3\2\2\2W\u0145\3\2\2\2Y\u0150\3\2\2\2["+
		"\u0155\3\2\2\2]\u015c\3\2\2\2_\u0161\3\2\2\2a\u0168\3\2\2\2c\u016f\3\2"+
		"\2\2e\u0173\3\2\2\2g\u0179\3\2\2\2i\u0180\3\2\2\2k\u0187\3\2\2\2m\u018d"+
		"\3\2\2\2o\u0190\3\2\2\2q\u0194\3\2\2\2s\u0199\3\2\2\2u\u019b\3\2\2\2w"+
		"\u019d\3\2\2\2y\u01a0\3\2\2\2{\u01a5\3\2\2\2}\u01a7\3\2\2\2\177\u01b0"+
		"\3\2\2\2\u0081\u01b7\3\2\2\2\u0083\u01b9\3\2\2\2\u0085\u01c2\3\2\2\2\u0087"+
		"\u01ce\3\2\2\2\u0089\u008a\7+\2\2\u008a\4\3\2\2\2\u008b\u008c\7\60\2\2"+
		"\u008c\6\3\2\2\2\u008d\u008e\7.\2\2\u008e\b\3\2\2\2\u008f\u0090\7/\2\2"+
		"\u0090\n\3\2\2\2\u0091\u0092\7,\2\2\u0092\f\3\2\2\2\u0093\u0094\7*\2\2"+
		"\u0094\16\3\2\2\2\u0095\u0096\7>\2\2\u0096\u0097\7@\2\2\u0097\20\3\2\2"+
		"\2\u0098\u0099\7>\2\2\u0099\22\3\2\2\2\u009a\u009b\7?\2\2\u009b\24\3\2"+
		"\2\2\u009c\u009d\7=\2\2\u009d\26\3\2\2\2\u009e\u009f\7>\2\2\u009f\u00a0"+
		"\7?\2\2\u00a0\30\3\2\2\2\u00a1\u00a2\7\64\2\2\u00a2\32\3\2\2\2\u00a3\u00a4"+
		"\7@\2\2\u00a4\34\3\2\2\2\u00a5\u00a6\7\65\2\2\u00a6\36\3\2\2\2\u00a7\u00a8"+
		"\7\63\2\2\u00a8 \3\2\2\2\u00a9\u00aa\7@\2\2\u00aa\u00ab\7?\2\2\u00ab\""+
		"\3\2\2\2\u00ac\u00ad\7\62\2\2\u00ad$\3\2\2\2\u00ae\u00af\7k\2\2\u00af"+
		"\u00b0\7p\2\2\u00b0\u00b1\7v\2\2\u00b1&\3\2\2\2\u00b2\u00b3\7e\2\2\u00b3"+
		"\u00b4\7j\2\2\u00b4\u00b5\7c\2\2\u00b5\u00b6\7t\2\2\u00b6(\3\2\2\2\u00b7"+
		"\u00b8\7h\2\2\u00b8\u00b9\7n\2\2\u00b9\u00ba\7q\2\2\u00ba\u00bb\7c\2\2"+
		"\u00bb\u00bc\7v\2\2\u00bc*\3\2\2\2\u00bd\u00be\7E\2\2\u00be\u00bf\7T\2"+
		"\2\u00bf\u00c0\7G\2\2\u00c0\u00c1\7C\2\2\u00c1\u00c2\7V\2\2\u00c2\u00c3"+
		"\7G\2\2\u00c3,\3\2\2\2\u00c4\u00c5\7F\2\2\u00c5\u00c6\7C\2\2\u00c6\u00c7"+
		"\7V\2\2\u00c7\u00c8\7C\2\2\u00c8\u00c9\7D\2\2\u00c9\u00ca\7C\2\2\u00ca"+
		"\u00cb\7U\2\2\u00cb\u00cc\7G\2\2\u00cc.\3\2\2\2\u00cd\u00ce\7F\2\2\u00ce"+
		"\u00cf\7C\2\2\u00cf\u00d0\7V\2\2\u00d0\u00d1\7C\2\2\u00d1\u00d2\7D\2\2"+
		"\u00d2\u00d3\7C\2\2\u00d3\u00d4\7U\2\2\u00d4\u00d5\7G\2\2\u00d5\u00d6"+
		"\7U\2\2\u00d6\60\3\2\2\2\u00d7\u00d8\7C\2\2\u00d8\u00d9\7N\2\2\u00d9\u00da"+
		"\7V\2\2\u00da\u00db\7G\2\2\u00db\u00dc\7T\2\2\u00dc\62\3\2\2\2\u00dd\u00de"+
		"\7T\2\2\u00de\u00df\7G\2\2\u00df\u00e0\7P\2\2\u00e0\u00e1\7C\2\2\u00e1"+
		"\u00e2\7O\2\2\u00e2\u00e3\7G\2\2\u00e3\64\3\2\2\2\u00e4\u00e5\7V\2\2\u00e5"+
		"\u00e6\7Q\2\2\u00e6\66\3\2\2\2\u00e7\u00e8\7F\2\2\u00e8\u00e9\7T\2\2\u00e9"+
		"\u00ea\7Q\2\2\u00ea\u00eb\7R\2\2\u00eb8\3\2\2\2\u00ec\u00ed\7W\2\2\u00ed"+
		"\u00ee\7U\2\2\u00ee\u00ef\7G\2\2\u00ef:\3\2\2\2\u00f0\u00f1\7U\2\2\u00f1"+
		"\u00f2\7J\2\2\u00f2\u00f3\7Q\2\2\u00f3\u00f4\7Y\2\2\u00f4<\3\2\2\2\u00f5"+
		"\u00f6\7V\2\2\u00f6\u00f7\7C\2\2\u00f7\u00f8\7D\2\2\u00f8\u00f9\7N\2\2"+
		"\u00f9\u00fa\7G\2\2\u00fa>\3\2\2\2\u00fb\u00fc\7V\2\2\u00fc\u00fd\7C\2"+
		"\2\u00fd\u00fe\7D\2\2\u00fe\u00ff\7N\2\2\u00ff\u0100\7G\2\2\u0100\u0101"+
		"\7U\2\2\u0101@\3\2\2\2\u0102\u0103\7R\2\2\u0103\u0104\7T\2\2\u0104\u0105"+
		"\7K\2\2\u0105\u0106\7O\2\2\u0106\u0107\7C\2\2\u0107\u0108\7T\2\2\u0108"+
		"\u0109\7[\2\2\u0109B\3\2\2\2\u010a\u010b\7M\2\2\u010b\u010c\7G\2\2\u010c"+
		"\u010d\7[\2\2\u010dD\3\2\2\2\u010e\u010f\7H\2\2\u010f\u0110\7Q\2\2\u0110"+
		"\u0111\7T\2\2\u0111\u0112\7G\2\2\u0112\u0113\7K\2\2\u0113\u0114\7I\2\2"+
		"\u0114\u0115\7P\2\2\u0115F\3\2\2\2\u0116\u0117\7E\2\2\u0117\u0118\7J\2"+
		"\2\u0118\u0119\7G\2\2\u0119\u011a\7E\2\2\u011a\u011b\7M\2\2\u011bH\3\2"+
		"\2\2\u011c\u011d\7T\2\2\u011d\u011e\7G\2\2\u011e\u011f\7H\2\2\u011f\u0120"+
		"\7G\2\2\u0120\u0121\7T\2\2\u0121\u0122\7G\2\2\u0122\u0123\7P\2\2\u0123"+
		"\u0124\7E\2\2\u0124\u0125\7G\2\2\u0125\u0126\7U\2\2\u0126J\3\2\2\2\u0127"+
		"\u0128\7P\2\2\u0128\u0129\7Q\2\2\u0129\u012a\7V\2\2\u012aL\3\2\2\2\u012b"+
		"\u012c\7Q\2\2\u012c\u012d\7T\2\2\u012dN\3\2\2\2\u012e\u012f\7C\2\2\u012f"+
		"\u0130\7P\2\2\u0130\u0131\7F\2\2\u0131P\3\2\2\2\u0132\u0133\7C\2\2\u0133"+
		"\u0134\7F\2\2\u0134\u0135\7F\2\2\u0135R\3\2\2\2\u0136\u0137\7E\2\2\u0137"+
		"\u0138\7Q\2\2\u0138\u0139\7N\2\2\u0139\u013a\7W\2\2\u013a\u013b\7O\2\2"+
		"\u013b\u013c\7P\2\2\u013cT\3\2\2\2\u013d\u013e\7E\2\2\u013e\u013f\7Q\2"+
		"\2\u013f\u0140\7N\2\2\u0140\u0141\7W\2\2\u0141\u0142\7O\2\2\u0142\u0143"+
		"\7P\2\2\u0143\u0144\7U\2\2\u0144V\3\2\2\2\u0145\u0146\7E\2\2\u0146\u0147"+
		"\7Q\2\2\u0147\u0148\7P\2\2\u0148\u0149\7U\2\2\u0149\u014a\7V\2\2\u014a"+
		"\u014b\7T\2\2\u014b\u014c\7C\2\2\u014c\u014d\7K\2\2\u014d\u014e\7P\2\2"+
		"\u014e\u014f\7V\2\2\u014fX\3\2\2\2\u0150\u0151\7H\2\2\u0151\u0152\7T\2"+
		"\2\u0152\u0153\7Q\2\2\u0153\u0154\7O\2\2\u0154Z\3\2\2\2\u0155\u0156\7"+
		"K\2\2\u0156\u0157\7P\2\2\u0157\u0158\7U\2\2\u0158\u0159\7G\2\2\u0159\u015a"+
		"\7T\2\2\u015a\u015b\7V\2\2\u015b\\\3\2\2\2\u015c\u015d\7K\2\2\u015d\u015e"+
		"\7P\2\2\u015e\u015f\7V\2\2\u015f\u0160\7Q\2\2\u0160^\3\2\2\2\u0161\u0162"+
		"\7X\2\2\u0162\u0163\7C\2\2\u0163\u0164\7N\2\2\u0164\u0165\7W\2\2\u0165"+
		"\u0166\7G\2\2\u0166\u0167\7U\2\2\u0167`\3\2\2\2\u0168\u0169\7W\2\2\u0169"+
		"\u016a\7R\2\2\u016a\u016b\7F\2\2\u016b\u016c\7C\2\2\u016c\u016d\7V\2\2"+
		"\u016d\u016e\7G\2\2\u016eb\3\2\2\2\u016f\u0170\7U\2\2\u0170\u0171\7G\2"+
		"\2\u0171\u0172\7V\2\2\u0172d\3\2\2\2\u0173\u0174\7Y\2\2\u0174\u0175\7"+
		"J\2\2\u0175\u0176\7G\2\2\u0176\u0177\7T\2\2\u0177\u0178\7G\2\2\u0178f"+
		"\3\2\2\2\u0179\u017a\7F\2\2\u017a\u017b\7G\2\2\u017b\u017c\7N\2\2\u017c"+
		"\u017d\7G\2\2\u017d\u017e\7V\2\2\u017e\u017f\7G\2\2\u017fh\3\2\2\2\u0180"+
		"\u0181\7U\2\2\u0181\u0182\7G\2\2\u0182\u0183\7N\2\2\u0183\u0184\7G\2\2"+
		"\u0184\u0185\7E\2\2\u0185\u0186\7V\2\2\u0186j\3\2\2\2\u0187\u0188\7Q\2"+
		"\2\u0188\u0189\7T\2\2\u0189\u018a\7F\2\2\u018a\u018b\7G\2\2\u018b\u018c"+
		"\7T\2\2\u018cl\3\2\2\2\u018d\u018e\7D\2\2\u018e\u018f\7[\2\2\u018fn\3"+
		"\2\2\2\u0190\u0191\7C\2\2\u0191\u0192\7U\2\2\u0192\u0193\7E\2\2\u0193"+
		"p\3\2\2\2\u0194\u0195\7F\2\2\u0195\u0196\7G\2\2\u0196\u0197\7U\2\2\u0197"+
		"\u0198\7E\2\2\u0198r\3\2\2\2\u0199\u019a\t\2\2\2\u019at\3\2\2\2\u019b"+
		"\u019c\4\62;\2\u019cv\3\2\2\2\u019d\u019e\t\3\2\2\u019ex\3\2\2\2\u019f"+
		"\u01a1\t\4\2\2\u01a0\u019f\3\2\2\2\u01a1z\3\2\2\2\u01a2\u01a6\5s:\2\u01a3"+
		"\u01a6\5u;\2\u01a4\u01a6\5w<\2\u01a5\u01a2\3\2\2\2\u01a5\u01a3\3\2\2\2"+
		"\u01a5\u01a4\3\2\2\2\u01a6|\3\2\2\2\u01a7\u01ad\5s:\2\u01a8\u01ac\5s:"+
		"\2\u01a9\u01ac\5u;\2\u01aa\u01ac\7a\2\2\u01ab\u01a8\3\2\2\2\u01ab\u01a9"+
		"\3\2\2\2\u01ab\u01aa\3\2\2\2\u01ac\u01af\3\2\2\2\u01ad\u01ab\3\2\2\2\u01ad"+
		"\u01ae\3\2\2\2\u01ae~\3\2\2\2\u01af\u01ad\3\2\2\2\u01b0\u01b4\5u;\2\u01b1"+
		"\u01b3\5u;\2\u01b2\u01b1\3\2\2\2\u01b3\u01b6\3\2\2\2\u01b4\u01b2\3\2\2"+
		"\2\u01b4\u01b5\3\2\2\2\u01b5\u0080\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7\u01b8"+
		"\5u;\2\u01b8\u0082\3\2\2\2\u01b9\u01bd\7)\2\2\u01ba\u01bc\5{>\2\u01bb"+
		"\u01ba\3\2\2\2\u01bc\u01bf\3\2\2\2\u01bd\u01bb\3\2\2\2\u01bd\u01be\3\2"+
		"\2\2\u01be\u01c0\3\2\2\2\u01bf\u01bd\3\2\2\2\u01c0\u01c1\7)\2\2\u01c1"+
		"\u0084\3\2\2\2\u01c2\u01c3\7\61\2\2\u01c3\u01c4\7\61\2\2\u01c4\u01c8\3"+
		"\2\2\2\u01c5\u01c7\n\5\2\2\u01c6\u01c5\3\2\2\2\u01c7\u01ca\3\2\2\2\u01c8"+
		"\u01c6\3\2\2\2\u01c8\u01c9\3\2\2\2\u01c9\u01cb\3\2\2\2\u01ca\u01c8\3\2"+
		"\2\2\u01cb\u01cc\bC\2\2\u01cc\u0086\3\2\2\2\u01cd\u01cf\t\6\2\2\u01ce"+
		"\u01cd\3\2\2\2\u01cf\u01d0\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2"+
		"\2\2\u01d1\u01d2\3\2\2\2\u01d2\u01d3\bD\3\2\u01d3\u0088\3\2\2\2\13\2\u01a0"+
		"\u01a5\u01ab\u01ad\u01b4\u01bd\u01c8\u01d0\4\2\3\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}