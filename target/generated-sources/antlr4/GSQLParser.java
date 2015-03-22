// Generated from GSQL.g4 by ANTLR 4.4
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GSQLParser extends Parser {
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
	public static final String[] tokenNames = {
		"<INVALID>", "'0'", "'1'", "'2'", "'3'", "'>='", "';'", "'<'", "'='", 
		"'>'", "'<='", "'<>'", "'('", "')'", "'*'", "','", "'-'", "'.'", "'int'", 
		"'char'", "'float'", "'CREATE'", "'DATABASE'", "'DATABASES'", "'ALTER'", 
		"'RENAME'", "'TO'", "'DROP'", "'USE'", "'SHOW'", "'TABLE'", "'TABLES'", 
		"'PRIMARY'", "'KEY'", "'FOREIGN'", "'CHECK'", "'REFERENCES'", "'NOT'", 
		"'OR'", "'AND'", "'ADD'", "'COLUMN'", "'COLUMNS'", "'CONSTRAINT'", "'FROM'", 
		"'INSERT'", "'INTO'", "'VALUES'", "'UPDATE'", "'SET'", "'WHERE'", "'DELETE'", 
		"'SELECT'", "'ORDER'", "'BY'", "'ASC'", "'DESC'", "Id", "Num", "SimpDigit", 
		"Char", "Comments", "WhitespaceDeclaration"
	};
	public static final int
		RULE_database = 0, RULE_createDatabase = 1, RULE_alterDatabase = 2, RULE_dropDatabase = 3, 
		RULE_useDatabase = 4, RULE_showDatabase = 5, RULE_tableInstruction = 6, 
		RULE_createTable = 7, RULE_constraint = 8, RULE_type = 9, RULE_date = 10, 
		RULE_expression = 11, RULE_andExpression = 12, RULE_eqExpression = 13, 
		RULE_relExpression = 14, RULE_unExpression = 15, RULE_eqOp = 16, RULE_relOp = 17, 
		RULE_orOp = 18, RULE_andOp = 19, RULE_alterTable = 20, RULE_action = 21, 
		RULE_dropTable = 22, RULE_showTables = 23, RULE_showColumns = 24, RULE_literal = 25, 
		RULE_int_literal = 26, RULE_float_literal = 27, RULE_char_literal = 28, 
		RULE_insertInto = 29, RULE_updateSet = 30, RULE_deleteFrom = 31, RULE_selectFrom = 32;
	public static final String[] ruleNames = {
		"database", "createDatabase", "alterDatabase", "dropDatabase", "useDatabase", 
		"showDatabase", "tableInstruction", "createTable", "constraint", "type", 
		"date", "expression", "andExpression", "eqExpression", "relExpression", 
		"unExpression", "eqOp", "relOp", "orOp", "andOp", "alterTable", "action", 
		"dropTable", "showTables", "showColumns", "literal", "int_literal", "float_literal", 
		"char_literal", "insertInto", "updateSet", "deleteFrom", "selectFrom"
	};

	@Override
	public String getGrammarFileName() { return "GSQL.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GSQLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class DatabaseContext extends ParserRuleContext {
		public UseDatabaseContext useDatabase() {
			return getRuleContext(UseDatabaseContext.class,0);
		}
		public AlterDatabaseContext alterDatabase() {
			return getRuleContext(AlterDatabaseContext.class,0);
		}
		public DropDatabaseContext dropDatabase() {
			return getRuleContext(DropDatabaseContext.class,0);
		}
		public ShowDatabaseContext showDatabase() {
			return getRuleContext(ShowDatabaseContext.class,0);
		}
		public TableInstructionContext tableInstruction() {
			return getRuleContext(TableInstructionContext.class,0);
		}
		public CreateDatabaseContext createDatabase() {
			return getRuleContext(CreateDatabaseContext.class,0);
		}
		public DatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_database; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitDatabase(this);
		}
	}

	public final DatabaseContext database() throws RecognitionException {
		DatabaseContext _localctx = new DatabaseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_database);
		try {
			setState(72);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66); createDatabase();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67); alterDatabase();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68); dropDatabase();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(69); useDatabase();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(70); showDatabase();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(71); tableInstruction();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateDatabaseContext extends ParserRuleContext {
		public TerminalNode DATABASE() { return getToken(GSQLParser.DATABASE, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode CREATE() { return getToken(GSQLParser.CREATE, 0); }
		public CreateDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterCreateDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitCreateDatabase(this);
		}
	}

	public final CreateDatabaseContext createDatabase() throws RecognitionException {
		CreateDatabaseContext _localctx = new CreateDatabaseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_createDatabase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74); match(CREATE);
			setState(75); match(DATABASE);
			setState(76); match(Id);
			setState(77); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlterDatabaseContext extends ParserRuleContext {
		public TerminalNode RENAME() { return getToken(GSQLParser.RENAME, 0); }
		public TerminalNode DATABASE() { return getToken(GSQLParser.DATABASE, 0); }
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode TO() { return getToken(GSQLParser.TO, 0); }
		public TerminalNode ALTER() { return getToken(GSQLParser.ALTER, 0); }
		public AlterDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterAlterDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitAlterDatabase(this);
		}
	}

	public final AlterDatabaseContext alterDatabase() throws RecognitionException {
		AlterDatabaseContext _localctx = new AlterDatabaseContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_alterDatabase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79); match(ALTER);
			setState(80); match(DATABASE);
			setState(81); match(Id);
			setState(82); match(RENAME);
			setState(83); match(TO);
			setState(84); match(Id);
			setState(85); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DropDatabaseContext extends ParserRuleContext {
		public TerminalNode DATABASE() { return getToken(GSQLParser.DATABASE, 0); }
		public TerminalNode DROP() { return getToken(GSQLParser.DROP, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public DropDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterDropDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitDropDatabase(this);
		}
	}

	public final DropDatabaseContext dropDatabase() throws RecognitionException {
		DropDatabaseContext _localctx = new DropDatabaseContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_dropDatabase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87); match(DROP);
			setState(88); match(DATABASE);
			setState(89); match(Id);
			setState(90); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UseDatabaseContext extends ParserRuleContext {
		public TerminalNode DATABASE() { return getToken(GSQLParser.DATABASE, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode USE() { return getToken(GSQLParser.USE, 0); }
		public UseDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_useDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterUseDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitUseDatabase(this);
		}
	}

	public final UseDatabaseContext useDatabase() throws RecognitionException {
		UseDatabaseContext _localctx = new UseDatabaseContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_useDatabase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92); match(USE);
			setState(93); match(DATABASE);
			setState(94); match(Id);
			setState(95); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShowDatabaseContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(GSQLParser.SHOW, 0); }
		public TerminalNode DATABASES() { return getToken(GSQLParser.DATABASES, 0); }
		public ShowDatabaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showDatabase; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterShowDatabase(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitShowDatabase(this);
		}
	}

	public final ShowDatabaseContext showDatabase() throws RecognitionException {
		ShowDatabaseContext _localctx = new ShowDatabaseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_showDatabase);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); match(SHOW);
			setState(98); match(DATABASES);
			setState(99); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TableInstructionContext extends ParserRuleContext {
		public AlterTableContext alterTable() {
			return getRuleContext(AlterTableContext.class,0);
		}
		public DropTableContext dropTable() {
			return getRuleContext(DropTableContext.class,0);
		}
		public DeleteFromContext deleteFrom() {
			return getRuleContext(DeleteFromContext.class,0);
		}
		public SelectFromContext selectFrom() {
			return getRuleContext(SelectFromContext.class,0);
		}
		public CreateTableContext createTable() {
			return getRuleContext(CreateTableContext.class,0);
		}
		public ShowColumnsContext showColumns() {
			return getRuleContext(ShowColumnsContext.class,0);
		}
		public InsertIntoContext insertInto() {
			return getRuleContext(InsertIntoContext.class,0);
		}
		public UpdateSetContext updateSet() {
			return getRuleContext(UpdateSetContext.class,0);
		}
		public ShowTablesContext showTables() {
			return getRuleContext(ShowTablesContext.class,0);
		}
		public TableInstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tableInstruction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterTableInstruction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitTableInstruction(this);
		}
	}

	public final TableInstructionContext tableInstruction() throws RecognitionException {
		TableInstructionContext _localctx = new TableInstructionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tableInstruction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(110);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(101); createTable();
				}
				break;
			case 2:
				{
				setState(102); alterTable();
				}
				break;
			case 3:
				{
				setState(103); dropTable();
				}
				break;
			case 4:
				{
				setState(104); showTables();
				}
				break;
			case 5:
				{
				setState(105); showColumns();
				}
				break;
			case 6:
				{
				setState(106); insertInto();
				}
				break;
			case 7:
				{
				setState(107); updateSet();
				}
				break;
			case 8:
				{
				setState(108); deleteFrom();
				}
				break;
			case 9:
				{
				setState(109); selectFrom();
				}
				break;
			}
			setState(112); match(T__11);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CreateTableContext extends ParserRuleContext {
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode CONSTRAINT() { return getToken(GSQLParser.CONSTRAINT, 0); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TerminalNode CREATE() { return getToken(GSQLParser.CREATE, 0); }
		public TerminalNode TABLE() { return getToken(GSQLParser.TABLE, 0); }
		public CreateTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_createTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterCreateTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitCreateTable(this);
		}
	}

	public final CreateTableContext createTable() throws RecognitionException {
		CreateTableContext _localctx = new CreateTableContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_createTable);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114); match(CREATE);
			setState(115); match(TABLE);
			setState(116); match(Id);
			setState(117); match(T__5);
			setState(128);
			_la = _input.LA(1);
			if (_la==Id) {
				{
				setState(118); match(Id);
				setState(119); type();
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(120); match(T__2);
					setState(121); match(Id);
					setState(122); type();
					}
					}
					setState(127);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(132);
			_la = _input.LA(1);
			if (_la==CONSTRAINT) {
				{
				setState(130); match(CONSTRAINT);
				setState(131); constraint();
				}
			}

			setState(134); match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConstraintContext extends ParserRuleContext {
		public Token name;
		public TerminalNode PRIMARY() { return getToken(GSQLParser.PRIMARY, 0); }
		public TerminalNode KEY() { return getToken(GSQLParser.KEY, 0); }
		public List<TerminalNode> CHECK() { return getTokens(GSQLParser.CHECK); }
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode REFERENCES() { return getToken(GSQLParser.REFERENCES, 0); }
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode FOREIGN() { return getToken(GSQLParser.FOREIGN, 0); }
		public TerminalNode CHECK(int i) {
			return getToken(GSQLParser.CHECK, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitConstraint(this);
		}
	}

	public final ConstraintContext constraint() throws RecognitionException {
		ConstraintContext _localctx = new ConstraintContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_constraint);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				setState(137);
				_la = _input.LA(1);
				if (_la==Id) {
					{
					setState(136); ((ConstraintContext)_localctx).name = match(Id);
					}
				}

				setState(139); match(PRIMARY);
				setState(140); match(KEY);
				setState(141); match(T__5);
				setState(142); match(Id);
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(143); match(T__2);
					setState(144); match(Id);
					}
					}
					setState(149);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(150); match(T__4);
				}
				break;
			case 2:
				{
				setState(152);
				_la = _input.LA(1);
				if (_la==Id) {
					{
					setState(151); ((ConstraintContext)_localctx).name = match(Id);
					}
				}

				setState(154); match(FOREIGN);
				setState(155); match(KEY);
				setState(156); match(T__5);
				setState(157); match(Id);
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(158); match(T__2);
					setState(159); match(Id);
					}
					}
					setState(164);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(165); match(T__4);
				setState(166); match(REFERENCES);
				setState(167); match(Id);
				setState(168); match(T__5);
				setState(169); match(Id);
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(170); match(T__2);
					setState(171); match(Id);
					}
					}
					setState(176);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(177); match(T__4);
				}
				break;
			}
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CHECK || _la==Id) {
				{
				{
				setState(181);
				_la = _input.LA(1);
				if (_la==Id) {
					{
					setState(180); ((ConstraintContext)_localctx).name = match(Id);
					}
				}

				setState(183); match(CHECK);
				setState(184); match(T__5);
				setState(185); expression(0);
				setState(186); match(T__4);
				}
				}
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Num() { return getToken(GSQLParser.Num, 0); }
		public DateContext date() {
			return getRuleContext(DateContext.class,0);
		}
		public TerminalNode CHAR() { return getToken(GSQLParser.CHAR, 0); }
		public TerminalNode INT() { return getToken(GSQLParser.INT, 0); }
		public TerminalNode FLOAT() { return getToken(GSQLParser.FLOAT, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitType(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type);
		try {
			setState(200);
			switch (_input.LA(1)) {
			case INT:
				enterOuterAlt(_localctx, 1);
				{
				setState(193); match(INT);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(194); match(FLOAT);
				}
				break;
			case T__15:
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(195); date();
				}
				break;
			case CHAR:
				enterOuterAlt(_localctx, 4);
				{
				setState(196); match(CHAR);
				setState(197); match(T__5);
				setState(198); match(Num);
				setState(199); match(T__4);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DateContext extends ParserRuleContext {
		public TerminalNode SimpDigit(int i) {
			return getToken(GSQLParser.SimpDigit, i);
		}
		public List<TerminalNode> SimpDigit() { return getTokens(GSQLParser.SimpDigit); }
		public DateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_date; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterDate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitDate(this);
		}
	}

	public final DateContext date() throws RecognitionException {
		DateContext _localctx = new DateContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_date);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			_la = _input.LA(1);
			if ( !(_la==T__15 || _la==T__14) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(203); match(SimpDigit);
			setState(204); match(SimpDigit);
			setState(205); match(SimpDigit);
			setState(206); match(T__1);
			setState(207);
			_la = _input.LA(1);
			if ( !(_la==T__16 || _la==T__15) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(208); match(SimpDigit);
			setState(209); match(T__1);
			setState(210);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__15) | (1L << T__14) | (1L << T__13))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(211); match(SimpDigit);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CondOrExpressionContext extends ExpressionContext {
		public OrOpContext orOp() {
			return getRuleContext(OrOpContext.class,0);
		}
		public AndExpressionContext andExpression() {
			return getRuleContext(AndExpressionContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public CondOrExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterCondOrExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitCondOrExpression(this);
		}
	}
	public static class ExpAndExpressionContext extends ExpressionContext {
		public AndExpressionContext andExpression() {
			return getRuleContext(AndExpressionContext.class,0);
		}
		public ExpAndExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterExpAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitExpAndExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 22;
		enterRecursionRule(_localctx, 22, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new ExpAndExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(214); andExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(222);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CondOrExpressionContext(new ExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_expression);
					setState(216);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(217); orOp();
					setState(218); andExpression(0);
					}
					} 
				}
				setState(224);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AndExpressionContext extends ParserRuleContext {
		public AndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andExpression; }
	 
		public AndExpressionContext() { }
		public void copyFrom(AndExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EqAndExpressionContext extends AndExpressionContext {
		public EqExpressionContext eqExpression() {
			return getRuleContext(EqExpressionContext.class,0);
		}
		public EqAndExpressionContext(AndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterEqAndExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitEqAndExpression(this);
		}
	}
	public static class CondExpressionContext extends AndExpressionContext {
		public EqExpressionContext eqExpression() {
			return getRuleContext(EqExpressionContext.class,0);
		}
		public AndExpressionContext andExpression() {
			return getRuleContext(AndExpressionContext.class,0);
		}
		public AndOpContext andOp() {
			return getRuleContext(AndOpContext.class,0);
		}
		public CondExpressionContext(AndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterCondExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitCondExpression(this);
		}
	}

	public final AndExpressionContext andExpression() throws RecognitionException {
		return andExpression(0);
	}

	private AndExpressionContext andExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		AndExpressionContext _localctx = new AndExpressionContext(_ctx, _parentState);
		AndExpressionContext _prevctx = _localctx;
		int _startState = 24;
		enterRecursionRule(_localctx, 24, RULE_andExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new EqAndExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(226); eqExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(234);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new CondExpressionContext(new AndExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_andExpression);
					setState(228);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(229); andOp();
					setState(230); eqExpression(0);
					}
					} 
				}
				setState(236);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,15,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class EqExpressionContext extends ParserRuleContext {
		public EqExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqExpression; }
	 
		public EqExpressionContext() { }
		public void copyFrom(EqExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class EqOpExpressionContext extends EqExpressionContext {
		public EqExpressionContext eqExpression() {
			return getRuleContext(EqExpressionContext.class,0);
		}
		public EqOpContext eqOp() {
			return getRuleContext(EqOpContext.class,0);
		}
		public RelExpressionContext relExpression() {
			return getRuleContext(RelExpressionContext.class,0);
		}
		public EqOpExpressionContext(EqExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterEqOpExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitEqOpExpression(this);
		}
	}
	public static class EqRelExpressionContext extends EqExpressionContext {
		public RelExpressionContext relExpression() {
			return getRuleContext(RelExpressionContext.class,0);
		}
		public EqRelExpressionContext(EqExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterEqRelExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitEqRelExpression(this);
		}
	}

	public final EqExpressionContext eqExpression() throws RecognitionException {
		return eqExpression(0);
	}

	private EqExpressionContext eqExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		EqExpressionContext _localctx = new EqExpressionContext(_ctx, _parentState);
		EqExpressionContext _prevctx = _localctx;
		int _startState = 26;
		enterRecursionRule(_localctx, 26, RULE_eqExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new EqRelExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(238); relExpression(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(246);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new EqOpExpressionContext(new EqExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_eqExpression);
					setState(240);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(241); eqOp();
					setState(242); relExpression(0);
					}
					} 
				}
				setState(248);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class RelExpressionContext extends ParserRuleContext {
		public RelExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relExpression; }
	 
		public RelExpressionContext() { }
		public void copyFrom(RelExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RelOpExpressionContext extends RelExpressionContext {
		public UnExpressionContext unExpression() {
			return getRuleContext(UnExpressionContext.class,0);
		}
		public RelOpContext relOp() {
			return getRuleContext(RelOpContext.class,0);
		}
		public RelExpressionContext relExpression() {
			return getRuleContext(RelExpressionContext.class,0);
		}
		public RelOpExpressionContext(RelExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterRelOpExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitRelOpExpression(this);
		}
	}
	public static class RelSumExpressionContext extends RelExpressionContext {
		public UnExpressionContext unExpression() {
			return getRuleContext(UnExpressionContext.class,0);
		}
		public RelSumExpressionContext(RelExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterRelSumExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitRelSumExpression(this);
		}
	}

	public final RelExpressionContext relExpression() throws RecognitionException {
		return relExpression(0);
	}

	private RelExpressionContext relExpression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RelExpressionContext _localctx = new RelExpressionContext(_ctx, _parentState);
		RelExpressionContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_relExpression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			_localctx = new RelSumExpressionContext(_localctx);
			_ctx = _localctx;
			_prevctx = _localctx;

			setState(250); unExpression();
			}
			_ctx.stop = _input.LT(-1);
			setState(258);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new RelOpExpressionContext(new RelExpressionContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_relExpression);
					setState(252);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(253); relOp();
					setState(254); unExpression();
					}
					} 
				}
				setState(260);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class UnExpressionContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(GSQLParser.NOT, 0); }
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public UnExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterUnExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitUnExpression(this);
		}
	}

	public final UnExpressionContext unExpression() throws RecognitionException {
		UnExpressionContext _localctx = new UnExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_unExpression);
		int _la;
		try {
			setState(266);
			switch (_input.LA(1)) {
			case NOT:
			case Id:
				enterOuterAlt(_localctx, 1);
				{
				setState(262);
				_la = _input.LA(1);
				if (_la==NOT) {
					{
					setState(261); match(NOT);
					}
				}

				setState(264); match(Id);
				}
				break;
			case Num:
			case Char:
				enterOuterAlt(_localctx, 2);
				{
				setState(265); literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqOpContext extends ParserRuleContext {
		public EqOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eqOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterEqOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitEqOp(this);
		}
	}

	public final EqOpContext eqOp() throws RecognitionException {
		EqOpContext _localctx = new EqOpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_eqOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			_la = _input.LA(1);
			if ( !(_la==T__9 || _la==T__6) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RelOpContext extends ParserRuleContext {
		public RelOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterRelOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitRelOp(this);
		}
	}

	public final RelOpContext relOp() throws RecognitionException {
		RelOpContext _localctx = new RelOpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_relOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(270);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__12) | (1L << T__10) | (1L << T__8) | (1L << T__7))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrOpContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(GSQLParser.OR, 0); }
		public OrOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterOrOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitOrOp(this);
		}
	}

	public final OrOpContext orOp() throws RecognitionException {
		OrOpContext _localctx = new OrOpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_orOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(272); match(OR);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AndOpContext extends ParserRuleContext {
		public TerminalNode AND() { return getToken(GSQLParser.AND, 0); }
		public AndOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterAndOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitAndOp(this);
		}
	}

	public final AndOpContext andOp() throws RecognitionException {
		AndOpContext _localctx = new AndOpContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_andOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274); match(AND);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlterTableContext extends ParserRuleContext {
		public AlterTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alterTable; }
	 
		public AlterTableContext() { }
		public void copyFrom(AlterTableContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ActionAlterTableContext extends AlterTableContext {
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode ALTER() { return getToken(GSQLParser.ALTER, 0); }
		public List<ActionContext> action() {
			return getRuleContexts(ActionContext.class);
		}
		public TerminalNode TABLE() { return getToken(GSQLParser.TABLE, 0); }
		public ActionContext action(int i) {
			return getRuleContext(ActionContext.class,i);
		}
		public ActionAlterTableContext(AlterTableContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterActionAlterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitActionAlterTable(this);
		}
	}
	public static class RenameAlterTableContext extends AlterTableContext {
		public TerminalNode RENAME() { return getToken(GSQLParser.RENAME, 0); }
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode TO() { return getToken(GSQLParser.TO, 0); }
		public TerminalNode ALTER() { return getToken(GSQLParser.ALTER, 0); }
		public TerminalNode TABLE() { return getToken(GSQLParser.TABLE, 0); }
		public RenameAlterTableContext(AlterTableContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterRenameAlterTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitRenameAlterTable(this);
		}
	}

	public final AlterTableContext alterTable() throws RecognitionException {
		AlterTableContext _localctx = new AlterTableContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_alterTable);
		int _la;
		try {
			setState(291);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new RenameAlterTableContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(276); match(ALTER);
				setState(277); match(TABLE);
				setState(278); match(Id);
				setState(279); match(RENAME);
				setState(280); match(TO);
				setState(281); match(Id);
				}
				break;
			case 2:
				_localctx = new ActionAlterTableContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(282); match(ALTER);
				setState(283); match(TABLE);
				setState(284); match(Id);
				setState(288);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DROP || _la==ADD) {
					{
					{
					setState(285); action();
					}
					}
					setState(290);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
	 
		public ActionContext() { }
		public void copyFrom(ActionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ActionDropConstraintContext extends ActionContext {
		public TerminalNode DROP() { return getToken(GSQLParser.DROP, 0); }
		public TerminalNode CONSTRAINT() { return getToken(GSQLParser.CONSTRAINT, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public ActionDropConstraintContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterActionDropConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitActionDropConstraint(this);
		}
	}
	public static class ActionAddColumnContext extends ActionContext {
		public TerminalNode COLUMN() { return getToken(GSQLParser.COLUMN, 0); }
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode ADD() { return getToken(GSQLParser.ADD, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode CONSTRAINT() { return getToken(GSQLParser.CONSTRAINT, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ActionAddColumnContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterActionAddColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitActionAddColumn(this);
		}
	}
	public static class ActionAddConstraintContext extends ActionContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode ADD() { return getToken(GSQLParser.ADD, 0); }
		public TerminalNode CONSTRAINT() { return getToken(GSQLParser.CONSTRAINT, 0); }
		public ActionAddConstraintContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterActionAddConstraint(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitActionAddConstraint(this);
		}
	}
	public static class ActionDropColumnContext extends ActionContext {
		public TerminalNode COLUMN() { return getToken(GSQLParser.COLUMN, 0); }
		public TerminalNode DROP() { return getToken(GSQLParser.DROP, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public ActionDropColumnContext(ActionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterActionDropColumn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitActionDropColumn(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_action);
		int _la;
		try {
			setState(310);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				_localctx = new ActionAddColumnContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(293); match(ADD);
				setState(294); match(COLUMN);
				setState(295); match(Id);
				setState(296); type();
				setState(299);
				_la = _input.LA(1);
				if (_la==CONSTRAINT) {
					{
					setState(297); match(CONSTRAINT);
					setState(298); constraint();
					}
				}

				}
				break;
			case 2:
				_localctx = new ActionAddConstraintContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(301); match(ADD);
				setState(302); match(CONSTRAINT);
				setState(303); constraint();
				}
				break;
			case 3:
				_localctx = new ActionDropColumnContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(304); match(DROP);
				setState(305); match(COLUMN);
				setState(306); match(Id);
				}
				break;
			case 4:
				_localctx = new ActionDropConstraintContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(307); match(DROP);
				setState(308); match(CONSTRAINT);
				setState(309); match(Id);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DropTableContext extends ParserRuleContext {
		public TerminalNode DROP() { return getToken(GSQLParser.DROP, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode TABLE() { return getToken(GSQLParser.TABLE, 0); }
		public DropTableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dropTable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterDropTable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitDropTable(this);
		}
	}

	public final DropTableContext dropTable() throws RecognitionException {
		DropTableContext _localctx = new DropTableContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_dropTable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312); match(DROP);
			setState(313); match(TABLE);
			setState(314); match(Id);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShowTablesContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(GSQLParser.SHOW, 0); }
		public TerminalNode TABLES() { return getToken(GSQLParser.TABLES, 0); }
		public ShowTablesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showTables; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterShowTables(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitShowTables(this);
		}
	}

	public final ShowTablesContext showTables() throws RecognitionException {
		ShowTablesContext _localctx = new ShowTablesContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_showTables);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316); match(SHOW);
			setState(317); match(TABLES);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ShowColumnsContext extends ParserRuleContext {
		public TerminalNode SHOW() { return getToken(GSQLParser.SHOW, 0); }
		public TerminalNode COLUMNS() { return getToken(GSQLParser.COLUMNS, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode FROM() { return getToken(GSQLParser.FROM, 0); }
		public ShowColumnsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_showColumns; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterShowColumns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitShowColumns(this);
		}
	}

	public final ShowColumnsContext showColumns() throws RecognitionException {
		ShowColumnsContext _localctx = new ShowColumnsContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_showColumns);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(319); match(SHOW);
			setState(320); match(COLUMNS);
			setState(321); match(FROM);
			setState(322); match(Id);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public Int_literalContext int_literal() {
			return getRuleContext(Int_literalContext.class,0);
		}
		public Float_literalContext float_literal() {
			return getRuleContext(Float_literalContext.class,0);
		}
		public Char_literalContext char_literal() {
			return getRuleContext(Char_literalContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitLiteral(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_literal);
		try {
			setState(327);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(324); int_literal();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(325); char_literal();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(326); float_literal();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Int_literalContext extends ParserRuleContext {
		public TerminalNode Num() { return getToken(GSQLParser.Num, 0); }
		public Int_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_int_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterInt_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitInt_literal(this);
		}
	}

	public final Int_literalContext int_literal() throws RecognitionException {
		Int_literalContext _localctx = new Int_literalContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_int_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(329); match(Num);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Float_literalContext extends ParserRuleContext {
		public TerminalNode Num(int i) {
			return getToken(GSQLParser.Num, i);
		}
		public List<TerminalNode> Num() { return getTokens(GSQLParser.Num); }
		public Float_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_float_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterFloat_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitFloat_literal(this);
		}
	}

	public final Float_literalContext float_literal() throws RecognitionException {
		Float_literalContext _localctx = new Float_literalContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_float_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(331); match(Num);
			setState(332); match(T__0);
			setState(333); match(Num);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Char_literalContext extends ParserRuleContext {
		public TerminalNode Char() { return getToken(GSQLParser.Char, 0); }
		public Char_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_char_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterChar_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitChar_literal(this);
		}
	}

	public final Char_literalContext char_literal() throws RecognitionException {
		Char_literalContext _localctx = new Char_literalContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_char_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(335); match(Char);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InsertIntoContext extends ParserRuleContext {
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public TerminalNode VALUES() { return getToken(GSQLParser.VALUES, 0); }
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode INSERT() { return getToken(GSQLParser.INSERT, 0); }
		public TerminalNode INTO() { return getToken(GSQLParser.INTO, 0); }
		public InsertIntoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_insertInto; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterInsertInto(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitInsertInto(this);
		}
	}

	public final InsertIntoContext insertInto() throws RecognitionException {
		InsertIntoContext _localctx = new InsertIntoContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_insertInto);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(337); match(INSERT);
			setState(338); match(INTO);
			setState(339); match(Id);
			setState(352);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(340); match(T__5);
				setState(349);
				_la = _input.LA(1);
				if (_la==Id) {
					{
					setState(341); match(Id);
					setState(346);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(342); match(T__2);
						setState(343); match(Id);
						}
						}
						setState(348);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(351); match(T__4);
				}
			}

			setState(354); match(VALUES);
			setState(355); match(T__5);
			setState(364);
			_la = _input.LA(1);
			if (_la==Num || _la==Char) {
				{
				setState(356); literal();
				setState(361);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(357); match(T__2);
					setState(358); literal();
					}
					}
					setState(363);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(366); match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UpdateSetContext extends ParserRuleContext {
		public List<TerminalNode> Char() { return getTokens(GSQLParser.Char); }
		public TerminalNode UPDATE() { return getToken(GSQLParser.UPDATE, 0); }
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode WHERE() { return getToken(GSQLParser.WHERE, 0); }
		public TerminalNode Char(int i) {
			return getToken(GSQLParser.Char, i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode SET() { return getToken(GSQLParser.SET, 0); }
		public UpdateSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_updateSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterUpdateSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitUpdateSet(this);
		}
	}

	public final UpdateSetContext updateSet() throws RecognitionException {
		UpdateSetContext _localctx = new UpdateSetContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_updateSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(368); match(UPDATE);
			setState(369); match(Id);
			setState(370); match(SET);
			{
			setState(371); match(Id);
			setState(372); match(T__9);
			setState(373); match(Char);
			}
			setState(381);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(375); match(T__2);
				setState(376); match(Id);
				setState(377); match(T__9);
				setState(378); match(Char);
				}
				}
				setState(383);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(386);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(384); match(WHERE);
				setState(385); expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeleteFromContext extends ParserRuleContext {
		public TerminalNode DELETE() { return getToken(GSQLParser.DELETE, 0); }
		public TerminalNode Id() { return getToken(GSQLParser.Id, 0); }
		public TerminalNode WHERE() { return getToken(GSQLParser.WHERE, 0); }
		public TerminalNode FROM() { return getToken(GSQLParser.FROM, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public DeleteFromContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_deleteFrom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterDeleteFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitDeleteFrom(this);
		}
	}

	public final DeleteFromContext deleteFrom() throws RecognitionException {
		DeleteFromContext _localctx = new DeleteFromContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_deleteFrom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388); match(DELETE);
			setState(389); match(FROM);
			setState(390); match(Id);
			setState(391); match(WHERE);
			setState(392); expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SelectFromContext extends ParserRuleContext {
		public List<TerminalNode> ASC() { return getTokens(GSQLParser.ASC); }
		public TerminalNode ORDER() { return getToken(GSQLParser.ORDER, 0); }
		public TerminalNode ASC(int i) {
			return getToken(GSQLParser.ASC, i);
		}
		public List<TerminalNode> Id() { return getTokens(GSQLParser.Id); }
		public TerminalNode Id(int i) {
			return getToken(GSQLParser.Id, i);
		}
		public TerminalNode WHERE() { return getToken(GSQLParser.WHERE, 0); }
		public TerminalNode DESC(int i) {
			return getToken(GSQLParser.DESC, i);
		}
		public TerminalNode FROM() { return getToken(GSQLParser.FROM, 0); }
		public TerminalNode SELECT() { return getToken(GSQLParser.SELECT, 0); }
		public List<TerminalNode> DESC() { return getTokens(GSQLParser.DESC); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode BY() { return getToken(GSQLParser.BY, 0); }
		public SelectFromContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_selectFrom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).enterSelectFrom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GSQLListener ) ((GSQLListener)listener).exitSelectFrom(this);
		}
	}

	public final SelectFromContext selectFrom() throws RecognitionException {
		SelectFromContext _localctx = new SelectFromContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_selectFrom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394); match(SELECT);
			setState(404);
			switch (_input.LA(1)) {
			case T__3:
				{
				setState(395); match(T__3);
				}
				break;
			case Id:
				{
				setState(396); match(Id);
				setState(401);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(397); match(T__2);
					setState(398); match(Id);
					}
					}
					setState(403);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(406); match(FROM);
			setState(407); match(Id);
			setState(412);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(408); match(T__2);
				setState(409); match(Id);
				}
				}
				setState(414);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(435);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(415); match(WHERE);
				setState(416); expression(0);
				setState(433);
				_la = _input.LA(1);
				if (_la==ORDER) {
					{
					setState(417); match(ORDER);
					setState(418); match(BY);
					setState(419); match(Id);
					setState(421);
					_la = _input.LA(1);
					if (_la==ASC || _la==DESC) {
						{
						setState(420);
						_la = _input.LA(1);
						if ( !(_la==ASC || _la==DESC) ) {
						_errHandler.recoverInline(this);
						}
						consume();
						}
					}

					setState(430);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__2) {
						{
						{
						setState(423); match(T__2);
						setState(424); match(Id);
						setState(426);
						_la = _input.LA(1);
						if (_la==ASC || _la==DESC) {
							{
							setState(425);
							_la = _input.LA(1);
							if ( !(_la==ASC || _la==DESC) ) {
							_errHandler.recoverInline(this);
							}
							consume();
							}
						}

						}
						}
						setState(432);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 11: return expression_sempred((ExpressionContext)_localctx, predIndex);
		case 12: return andExpression_sempred((AndExpressionContext)_localctx, predIndex);
		case 13: return eqExpression_sempred((EqExpressionContext)_localctx, predIndex);
		case 14: return relExpression_sempred((RelExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean andExpression_sempred(AndExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean eqExpression_sempred(EqExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean relExpression_sempred(RelExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3: return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@\u01b8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\3\2\3\2\3\2\3\2\3\2\3\2\5\2K\n\2\3\3\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3\7"+
		"\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bq\n\b\3\b\3\b\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t~\n\t\f\t\16\t\u0081\13\t\5\t\u0083"+
		"\n\t\3\t\3\t\5\t\u0087\n\t\3\t\3\t\3\n\5\n\u008c\n\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\7\n\u0094\n\n\f\n\16\n\u0097\13\n\3\n\3\n\5\n\u009b\n\n\3\n\3\n"+
		"\3\n\3\n\3\n\3\n\7\n\u00a3\n\n\f\n\16\n\u00a6\13\n\3\n\3\n\3\n\3\n\3\n"+
		"\3\n\3\n\7\n\u00af\n\n\f\n\16\n\u00b2\13\n\3\n\5\n\u00b5\n\n\3\n\5\n\u00b8"+
		"\n\n\3\n\3\n\3\n\3\n\3\n\7\n\u00bf\n\n\f\n\16\n\u00c2\13\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\5\13\u00cb\n\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3"+
		"\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00df\n\r\f\r\16\r\u00e2"+
		"\13\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16\u00eb\n\16\f\16\16\16\u00ee"+
		"\13\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\7\17\u00f7\n\17\f\17\16\17\u00fa"+
		"\13\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u0103\n\20\f\20\16\20\u0106"+
		"\13\20\3\21\5\21\u0109\n\21\3\21\3\21\5\21\u010d\n\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3\26\3"+
		"\26\7\26\u0121\n\26\f\26\16\26\u0124\13\26\5\26\u0126\n\26\3\27\3\27\3"+
		"\27\3\27\3\27\3\27\5\27\u012e\n\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\5\27\u0139\n\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32"+
		"\3\32\3\32\3\32\3\33\3\33\3\33\5\33\u014a\n\33\3\34\3\34\3\35\3\35\3\35"+
		"\3\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\7\37\u015b\n\37\f\37"+
		"\16\37\u015e\13\37\5\37\u0160\n\37\3\37\5\37\u0163\n\37\3\37\3\37\3\37"+
		"\3\37\3\37\7\37\u016a\n\37\f\37\16\37\u016d\13\37\5\37\u016f\n\37\3\37"+
		"\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \7 \u017e\n \f \16 \u0181\13 \3"+
		" \3 \5 \u0185\n \3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\7\"\u0192\n\"\f"+
		"\"\16\"\u0195\13\"\5\"\u0197\n\"\3\"\3\"\3\"\3\"\7\"\u019d\n\"\f\"\16"+
		"\"\u01a0\13\"\3\"\3\"\3\"\3\"\3\"\3\"\5\"\u01a8\n\"\3\"\3\"\3\"\5\"\u01ad"+
		"\n\"\7\"\u01af\n\"\f\"\16\"\u01b2\13\"\5\"\u01b4\n\"\5\"\u01b6\n\"\3\""+
		"\2\6\30\32\34\36#\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62"+
		"\64\668:<>@B\2\b\3\2\4\5\3\2\3\4\3\2\3\6\4\2\n\n\r\r\5\2\7\7\t\t\13\f"+
		"\3\29:\u01ce\2J\3\2\2\2\4L\3\2\2\2\6Q\3\2\2\2\bY\3\2\2\2\n^\3\2\2\2\f"+
		"c\3\2\2\2\16p\3\2\2\2\20t\3\2\2\2\22\u00b4\3\2\2\2\24\u00ca\3\2\2\2\26"+
		"\u00cc\3\2\2\2\30\u00d7\3\2\2\2\32\u00e3\3\2\2\2\34\u00ef\3\2\2\2\36\u00fb"+
		"\3\2\2\2 \u010c\3\2\2\2\"\u010e\3\2\2\2$\u0110\3\2\2\2&\u0112\3\2\2\2"+
		"(\u0114\3\2\2\2*\u0125\3\2\2\2,\u0138\3\2\2\2.\u013a\3\2\2\2\60\u013e"+
		"\3\2\2\2\62\u0141\3\2\2\2\64\u0149\3\2\2\2\66\u014b\3\2\2\28\u014d\3\2"+
		"\2\2:\u0151\3\2\2\2<\u0153\3\2\2\2>\u0172\3\2\2\2@\u0186\3\2\2\2B\u018c"+
		"\3\2\2\2DK\5\4\3\2EK\5\6\4\2FK\5\b\5\2GK\5\n\6\2HK\5\f\7\2IK\5\16\b\2"+
		"JD\3\2\2\2JE\3\2\2\2JF\3\2\2\2JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\3\3\2\2"+
		"\2LM\7\27\2\2MN\7\30\2\2NO\7;\2\2OP\7\b\2\2P\5\3\2\2\2QR\7\32\2\2RS\7"+
		"\30\2\2ST\7;\2\2TU\7\33\2\2UV\7\34\2\2VW\7;\2\2WX\7\b\2\2X\7\3\2\2\2Y"+
		"Z\7\35\2\2Z[\7\30\2\2[\\\7;\2\2\\]\7\b\2\2]\t\3\2\2\2^_\7\36\2\2_`\7\30"+
		"\2\2`a\7;\2\2ab\7\b\2\2b\13\3\2\2\2cd\7\37\2\2de\7\31\2\2ef\7\b\2\2f\r"+
		"\3\2\2\2gq\5\20\t\2hq\5*\26\2iq\5.\30\2jq\5\60\31\2kq\5\62\32\2lq\5<\37"+
		"\2mq\5> \2nq\5@!\2oq\5B\"\2pg\3\2\2\2ph\3\2\2\2pi\3\2\2\2pj\3\2\2\2pk"+
		"\3\2\2\2pl\3\2\2\2pm\3\2\2\2pn\3\2\2\2po\3\2\2\2qr\3\2\2\2rs\7\b\2\2s"+
		"\17\3\2\2\2tu\7\27\2\2uv\7 \2\2vw\7;\2\2w\u0082\7\16\2\2xy\7;\2\2y\177"+
		"\5\24\13\2z{\7\21\2\2{|\7;\2\2|~\5\24\13\2}z\3\2\2\2~\u0081\3\2\2\2\177"+
		"}\3\2\2\2\177\u0080\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0082"+
		"x\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0086\3\2\2\2\u0084\u0085\7-\2\2\u0085"+
		"\u0087\5\22\n\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3"+
		"\2\2\2\u0088\u0089\7\17\2\2\u0089\21\3\2\2\2\u008a\u008c\7;\2\2\u008b"+
		"\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u008e\7\""+
		"\2\2\u008e\u008f\7#\2\2\u008f\u0090\7\16\2\2\u0090\u0095\7;\2\2\u0091"+
		"\u0092\7\21\2\2\u0092\u0094\7;\2\2\u0093\u0091\3\2\2\2\u0094\u0097\3\2"+
		"\2\2\u0095\u0093\3\2\2\2\u0095\u0096\3\2\2\2\u0096\u0098\3\2\2\2\u0097"+
		"\u0095\3\2\2\2\u0098\u00b5\7\17\2\2\u0099\u009b\7;\2\2\u009a\u0099\3\2"+
		"\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009d\7$\2\2\u009d"+
		"\u009e\7#\2\2\u009e\u009f\7\16\2\2\u009f\u00a4\7;\2\2\u00a0\u00a1\7\21"+
		"\2\2\u00a1\u00a3\7;\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a6\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7\3\2\2\2\u00a6\u00a4\3\2"+
		"\2\2\u00a7\u00a8\7\17\2\2\u00a8\u00a9\7&\2\2\u00a9\u00aa\7;\2\2\u00aa"+
		"\u00ab\7\16\2\2\u00ab\u00b0\7;\2\2\u00ac\u00ad\7\21\2\2\u00ad\u00af\7"+
		";\2\2\u00ae\u00ac\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0"+
		"\u00b1\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b5\7\17"+
		"\2\2\u00b4\u008b\3\2\2\2\u00b4\u009a\3\2\2\2\u00b5\u00c0\3\2\2\2\u00b6"+
		"\u00b8\7;\2\2\u00b7\u00b6\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8\u00b9\3\2"+
		"\2\2\u00b9\u00ba\7%\2\2\u00ba\u00bb\7\16\2\2\u00bb\u00bc\5\30\r\2\u00bc"+
		"\u00bd\7\17\2\2\u00bd\u00bf\3\2\2\2\u00be\u00b7\3\2\2\2\u00bf\u00c2\3"+
		"\2\2\2\u00c0\u00be\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\23\3\2\2\2\u00c2"+
		"\u00c0\3\2\2\2\u00c3\u00cb\7\24\2\2\u00c4\u00cb\7\26\2\2\u00c5\u00cb\5"+
		"\26\f\2\u00c6\u00c7\7\25\2\2\u00c7\u00c8\7\16\2\2\u00c8\u00c9\7<\2\2\u00c9"+
		"\u00cb\7\17\2\2\u00ca\u00c3\3\2\2\2\u00ca\u00c4\3\2\2\2\u00ca\u00c5\3"+
		"\2\2\2\u00ca\u00c6\3\2\2\2\u00cb\25\3\2\2\2\u00cc\u00cd\t\2\2\2\u00cd"+
		"\u00ce\7=\2\2\u00ce\u00cf\7=\2\2\u00cf\u00d0\7=\2\2\u00d0\u00d1\7\22\2"+
		"\2\u00d1\u00d2\t\3\2\2\u00d2\u00d3\7=\2\2\u00d3\u00d4\7\22\2\2\u00d4\u00d5"+
		"\t\4\2\2\u00d5\u00d6\7=\2\2\u00d6\27\3\2\2\2\u00d7\u00d8\b\r\1\2\u00d8"+
		"\u00d9\5\32\16\2\u00d9\u00e0\3\2\2\2\u00da\u00db\f\3\2\2\u00db\u00dc\5"+
		"&\24\2\u00dc\u00dd\5\32\16\2\u00dd\u00df\3\2\2\2\u00de\u00da\3\2\2\2\u00df"+
		"\u00e2\3\2\2\2\u00e0\u00de\3\2\2\2\u00e0\u00e1\3\2\2\2\u00e1\31\3\2\2"+
		"\2\u00e2\u00e0\3\2\2\2\u00e3\u00e4\b\16\1\2\u00e4\u00e5\5\34\17\2\u00e5"+
		"\u00ec\3\2\2\2\u00e6\u00e7\f\3\2\2\u00e7\u00e8\5(\25\2\u00e8\u00e9\5\34"+
		"\17\2\u00e9\u00eb\3\2\2\2\u00ea\u00e6\3\2\2\2\u00eb\u00ee\3\2\2\2\u00ec"+
		"\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00ed\33\3\2\2\2\u00ee\u00ec\3\2\2"+
		"\2\u00ef\u00f0\b\17\1\2\u00f0\u00f1\5\36\20\2\u00f1\u00f8\3\2\2\2\u00f2"+
		"\u00f3\f\3\2\2\u00f3\u00f4\5\"\22\2\u00f4\u00f5\5\36\20\2\u00f5\u00f7"+
		"\3\2\2\2\u00f6\u00f2\3\2\2\2\u00f7\u00fa\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8"+
		"\u00f9\3\2\2\2\u00f9\35\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fc\b\20\1"+
		"\2\u00fc\u00fd\5 \21\2\u00fd\u0104\3\2\2\2\u00fe\u00ff\f\3\2\2\u00ff\u0100"+
		"\5$\23\2\u0100\u0101\5 \21\2\u0101\u0103\3\2\2\2\u0102\u00fe\3\2\2\2\u0103"+
		"\u0106\3\2\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\37\3\2\2"+
		"\2\u0106\u0104\3\2\2\2\u0107\u0109\7\'\2\2\u0108\u0107\3\2\2\2\u0108\u0109"+
		"\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010d\7;\2\2\u010b\u010d\5\64\33\2"+
		"\u010c\u0108\3\2\2\2\u010c\u010b\3\2\2\2\u010d!\3\2\2\2\u010e\u010f\t"+
		"\5\2\2\u010f#\3\2\2\2\u0110\u0111\t\6\2\2\u0111%\3\2\2\2\u0112\u0113\7"+
		"(\2\2\u0113\'\3\2\2\2\u0114\u0115\7)\2\2\u0115)\3\2\2\2\u0116\u0117\7"+
		"\32\2\2\u0117\u0118\7 \2\2\u0118\u0119\7;\2\2\u0119\u011a\7\33\2\2\u011a"+
		"\u011b\7\34\2\2\u011b\u0126\7;\2\2\u011c\u011d\7\32\2\2\u011d\u011e\7"+
		" \2\2\u011e\u0122\7;\2\2\u011f\u0121\5,\27\2\u0120\u011f\3\2\2\2\u0121"+
		"\u0124\3\2\2\2\u0122\u0120\3\2\2\2\u0122\u0123\3\2\2\2\u0123\u0126\3\2"+
		"\2\2\u0124\u0122\3\2\2\2\u0125\u0116\3\2\2\2\u0125\u011c\3\2\2\2\u0126"+
		"+\3\2\2\2\u0127\u0128\7*\2\2\u0128\u0129\7+\2\2\u0129\u012a\7;\2\2\u012a"+
		"\u012d\5\24\13\2\u012b\u012c\7-\2\2\u012c\u012e\5\22\n\2\u012d\u012b\3"+
		"\2\2\2\u012d\u012e\3\2\2\2\u012e\u0139\3\2\2\2\u012f\u0130\7*\2\2\u0130"+
		"\u0131\7-\2\2\u0131\u0139\5\22\n\2\u0132\u0133\7\35\2\2\u0133\u0134\7"+
		"+\2\2\u0134\u0139\7;\2\2\u0135\u0136\7\35\2\2\u0136\u0137\7-\2\2\u0137"+
		"\u0139\7;\2\2\u0138\u0127\3\2\2\2\u0138\u012f\3\2\2\2\u0138\u0132\3\2"+
		"\2\2\u0138\u0135\3\2\2\2\u0139-\3\2\2\2\u013a\u013b\7\35\2\2\u013b\u013c"+
		"\7 \2\2\u013c\u013d\7;\2\2\u013d/\3\2\2\2\u013e\u013f\7\37\2\2\u013f\u0140"+
		"\7!\2\2\u0140\61\3\2\2\2\u0141\u0142\7\37\2\2\u0142\u0143\7,\2\2\u0143"+
		"\u0144\7.\2\2\u0144\u0145\7;\2\2\u0145\63\3\2\2\2\u0146\u014a\5\66\34"+
		"\2\u0147\u014a\5:\36\2\u0148\u014a\58\35\2\u0149\u0146\3\2\2\2\u0149\u0147"+
		"\3\2\2\2\u0149\u0148\3\2\2\2\u014a\65\3\2\2\2\u014b\u014c\7<\2\2\u014c"+
		"\67\3\2\2\2\u014d\u014e\7<\2\2\u014e\u014f\7\23\2\2\u014f\u0150\7<\2\2"+
		"\u01509\3\2\2\2\u0151\u0152\7>\2\2\u0152;\3\2\2\2\u0153\u0154\7/\2\2\u0154"+
		"\u0155\7\60\2\2\u0155\u0162\7;\2\2\u0156\u015f\7\16\2\2\u0157\u015c\7"+
		";\2\2\u0158\u0159\7\21\2\2\u0159\u015b\7;\2\2\u015a\u0158\3\2\2\2\u015b"+
		"\u015e\3\2\2\2\u015c\u015a\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u0160\3\2"+
		"\2\2\u015e\u015c\3\2\2\2\u015f\u0157\3\2\2\2\u015f\u0160\3\2\2\2\u0160"+
		"\u0161\3\2\2\2\u0161\u0163\7\17\2\2\u0162\u0156\3\2\2\2\u0162\u0163\3"+
		"\2\2\2\u0163\u0164\3\2\2\2\u0164\u0165\7\61\2\2\u0165\u016e\7\16\2\2\u0166"+
		"\u016b\5\64\33\2\u0167\u0168\7\21\2\2\u0168\u016a\5\64\33\2\u0169\u0167"+
		"\3\2\2\2\u016a\u016d\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"\u016f\3\2\2\2\u016d\u016b\3\2\2\2\u016e\u0166\3\2\2\2\u016e\u016f\3\2"+
		"\2\2\u016f\u0170\3\2\2\2\u0170\u0171\7\17\2\2\u0171=\3\2\2\2\u0172\u0173"+
		"\7\62\2\2\u0173\u0174\7;\2\2\u0174\u0175\7\63\2\2\u0175\u0176\7;\2\2\u0176"+
		"\u0177\7\n\2\2\u0177\u0178\7>\2\2\u0178\u017f\3\2\2\2\u0179\u017a\7\21"+
		"\2\2\u017a\u017b\7;\2\2\u017b\u017c\7\n\2\2\u017c\u017e\7>\2\2\u017d\u0179"+
		"\3\2\2\2\u017e\u0181\3\2\2\2\u017f\u017d\3\2\2\2\u017f\u0180\3\2\2\2\u0180"+
		"\u0184\3\2\2\2\u0181\u017f\3\2\2\2\u0182\u0183\7\64\2\2\u0183\u0185\5"+
		"\30\r\2\u0184\u0182\3\2\2\2\u0184\u0185\3\2\2\2\u0185?\3\2\2\2\u0186\u0187"+
		"\7\65\2\2\u0187\u0188\7.\2\2\u0188\u0189\7;\2\2\u0189\u018a\7\64\2\2\u018a"+
		"\u018b\5\30\r\2\u018bA\3\2\2\2\u018c\u0196\7\66\2\2\u018d\u0197\7\20\2"+
		"\2\u018e\u0193\7;\2\2\u018f\u0190\7\21\2\2\u0190\u0192\7;\2\2\u0191\u018f"+
		"\3\2\2\2\u0192\u0195\3\2\2\2\u0193\u0191\3\2\2\2\u0193\u0194\3\2\2\2\u0194"+
		"\u0197\3\2\2\2\u0195\u0193\3\2\2\2\u0196\u018d\3\2\2\2\u0196\u018e\3\2"+
		"\2\2\u0197\u0198\3\2\2\2\u0198\u0199\7.\2\2\u0199\u019e\7;\2\2\u019a\u019b"+
		"\7\21\2\2\u019b\u019d\7;\2\2\u019c\u019a\3\2\2\2\u019d\u01a0\3\2\2\2\u019e"+
		"\u019c\3\2\2\2\u019e\u019f\3\2\2\2\u019f\u01b5\3\2\2\2\u01a0\u019e\3\2"+
		"\2\2\u01a1\u01a2\7\64\2\2\u01a2\u01b3\5\30\r\2\u01a3\u01a4\7\67\2\2\u01a4"+
		"\u01a5\78\2\2\u01a5\u01a7\7;\2\2\u01a6\u01a8\t\7\2\2\u01a7\u01a6\3\2\2"+
		"\2\u01a7\u01a8\3\2\2\2\u01a8\u01b0\3\2\2\2\u01a9\u01aa\7\21\2\2\u01aa"+
		"\u01ac\7;\2\2\u01ab\u01ad\t\7\2\2\u01ac\u01ab\3\2\2\2\u01ac\u01ad\3\2"+
		"\2\2\u01ad\u01af\3\2\2\2\u01ae\u01a9\3\2\2\2\u01af\u01b2\3\2\2\2\u01b0"+
		"\u01ae\3\2\2\2\u01b0\u01b1\3\2\2\2\u01b1\u01b4\3\2\2\2\u01b2\u01b0\3\2"+
		"\2\2\u01b3\u01a3\3\2\2\2\u01b3\u01b4\3\2\2\2\u01b4\u01b6\3\2\2\2\u01b5"+
		"\u01a1\3\2\2\2\u01b5\u01b6\3\2\2\2\u01b6C\3\2\2\2*Jp\177\u0082\u0086\u008b"+
		"\u0095\u009a\u00a4\u00b0\u00b4\u00b7\u00c0\u00ca\u00e0\u00ec\u00f8\u0104"+
		"\u0108\u010c\u0122\u0125\u012d\u0138\u0149\u015c\u015f\u0162\u016b\u016e"+
		"\u017f\u0184\u0193\u0196\u019e\u01a7\u01ac\u01b0\u01b3\u01b5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}