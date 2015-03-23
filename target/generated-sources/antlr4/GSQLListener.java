// Generated from GSQL.g4 by ANTLR 4.4
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GSQLParser}.
 */
public interface GSQLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GSQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void enterCreateTable(@NotNull GSQLParser.CreateTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#createTable}.
	 * @param ctx the parse tree
	 */
	void exitCreateTable(@NotNull GSQLParser.CreateTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqOpExpression}
	 * labeled alternative in {@link GSQLParser#eqExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqOpExpression(@NotNull GSQLParser.EqOpExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqOpExpression}
	 * labeled alternative in {@link GSQLParser#eqExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqOpExpression(@NotNull GSQLParser.EqOpExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void enterSelectFrom(@NotNull GSQLParser.SelectFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#selectFrom}.
	 * @param ctx the parse tree
	 */
	void exitSelectFrom(@NotNull GSQLParser.SelectFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void enterAlterDatabase(@NotNull GSQLParser.AlterDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#alterDatabase}.
	 * @param ctx the parse tree
	 */
	void exitAlterDatabase(@NotNull GSQLParser.AlterDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code actionAddColumn}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void enterActionAddColumn(@NotNull GSQLParser.ActionAddColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code actionAddColumn}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void exitActionAddColumn(@NotNull GSQLParser.ActionAddColumnContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void enterConstraint(@NotNull GSQLParser.ConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#constraint}.
	 * @param ctx the parse tree
	 */
	void exitConstraint(@NotNull GSQLParser.ConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void enterDropDatabase(@NotNull GSQLParser.DropDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#dropDatabase}.
	 * @param ctx the parse tree
	 */
	void exitDropDatabase(@NotNull GSQLParser.DropDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void enterDeleteFrom(@NotNull GSQLParser.DeleteFromContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#deleteFrom}.
	 * @param ctx the parse tree
	 */
	void exitDeleteFrom(@NotNull GSQLParser.DeleteFromContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#showColumns}.
	 * @param ctx the parse tree
	 */
	void enterShowColumns(@NotNull GSQLParser.ShowColumnsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#showColumns}.
	 * @param ctx the parse tree
	 */
	void exitShowColumns(@NotNull GSQLParser.ShowColumnsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#date}.
	 * @param ctx the parse tree
	 */
	void enterDate(@NotNull GSQLParser.DateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#date}.
	 * @param ctx the parse tree
	 */
	void exitDate(@NotNull GSQLParser.DateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(@NotNull GSQLParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(@NotNull GSQLParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#unExpression}.
	 * @param ctx the parse tree
	 */
	void enterUnExpression(@NotNull GSQLParser.UnExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#unExpression}.
	 * @param ctx the parse tree
	 */
	void exitUnExpression(@NotNull GSQLParser.UnExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#showTables}.
	 * @param ctx the parse tree
	 */
	void enterShowTables(@NotNull GSQLParser.ShowTablesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#showTables}.
	 * @param ctx the parse tree
	 */
	void exitShowTables(@NotNull GSQLParser.ShowTablesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void enterCreateDatabase(@NotNull GSQLParser.CreateDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#createDatabase}.
	 * @param ctx the parse tree
	 */
	void exitCreateDatabase(@NotNull GSQLParser.CreateDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expAndExpression}
	 * labeled alternative in {@link GSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpAndExpression(@NotNull GSQLParser.ExpAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expAndExpression}
	 * labeled alternative in {@link GSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpAndExpression(@NotNull GSQLParser.ExpAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condOrExpression}
	 * labeled alternative in {@link GSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCondOrExpression(@NotNull GSQLParser.CondOrExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condOrExpression}
	 * labeled alternative in {@link GSQLParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCondOrExpression(@NotNull GSQLParser.CondOrExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code actionDropColumn}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void enterActionDropColumn(@NotNull GSQLParser.ActionDropColumnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code actionDropColumn}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void exitActionDropColumn(@NotNull GSQLParser.ActionDropColumnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condExpression}
	 * labeled alternative in {@link GSQLParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterCondExpression(@NotNull GSQLParser.CondExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condExpression}
	 * labeled alternative in {@link GSQLParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitCondExpression(@NotNull GSQLParser.CondExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void enterInsertInto(@NotNull GSQLParser.InsertIntoContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#insertInto}.
	 * @param ctx the parse tree
	 */
	void exitInsertInto(@NotNull GSQLParser.InsertIntoContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#showDatabase}.
	 * @param ctx the parse tree
	 */
	void enterShowDatabase(@NotNull GSQLParser.ShowDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#showDatabase}.
	 * @param ctx the parse tree
	 */
	void exitShowDatabase(@NotNull GSQLParser.ShowDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#relOp}.
	 * @param ctx the parse tree
	 */
	void enterRelOp(@NotNull GSQLParser.RelOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#relOp}.
	 * @param ctx the parse tree
	 */
	void exitRelOp(@NotNull GSQLParser.RelOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void enterInt_literal(@NotNull GSQLParser.Int_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#int_literal}.
	 * @param ctx the parse tree
	 */
	void exitInt_literal(@NotNull GSQLParser.Int_literalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqAndExpression}
	 * labeled alternative in {@link GSQLParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqAndExpression(@NotNull GSQLParser.EqAndExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqAndExpression}
	 * labeled alternative in {@link GSQLParser#andExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqAndExpression(@NotNull GSQLParser.EqAndExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relOpExpression}
	 * labeled alternative in {@link GSQLParser#relExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelOpExpression(@NotNull GSQLParser.RelOpExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relOpExpression}
	 * labeled alternative in {@link GSQLParser#relExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelOpExpression(@NotNull GSQLParser.RelOpExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code renameAlterTable}
	 * labeled alternative in {@link GSQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void enterRenameAlterTable(@NotNull GSQLParser.RenameAlterTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code renameAlterTable}
	 * labeled alternative in {@link GSQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void exitRenameAlterTable(@NotNull GSQLParser.RenameAlterTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code actionDropConstraint}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void enterActionDropConstraint(@NotNull GSQLParser.ActionDropConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code actionDropConstraint}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void exitActionDropConstraint(@NotNull GSQLParser.ActionDropConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#andOp}.
	 * @param ctx the parse tree
	 */
	void enterAndOp(@NotNull GSQLParser.AndOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#andOp}.
	 * @param ctx the parse tree
	 */
	void exitAndOp(@NotNull GSQLParser.AndOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#database}.
	 * @param ctx the parse tree
	 */
	void enterDatabase(@NotNull GSQLParser.DatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#database}.
	 * @param ctx the parse tree
	 */
	void exitDatabase(@NotNull GSQLParser.DatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void enterChar_literal(@NotNull GSQLParser.Char_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#char_literal}.
	 * @param ctx the parse tree
	 */
	void exitChar_literal(@NotNull GSQLParser.Char_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void enterFloat_literal(@NotNull GSQLParser.Float_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#float_literal}.
	 * @param ctx the parse tree
	 */
	void exitFloat_literal(@NotNull GSQLParser.Float_literalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eqRelExpression}
	 * labeled alternative in {@link GSQLParser#eqExpression}.
	 * @param ctx the parse tree
	 */
	void enterEqRelExpression(@NotNull GSQLParser.EqRelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eqRelExpression}
	 * labeled alternative in {@link GSQLParser#eqExpression}.
	 * @param ctx the parse tree
	 */
	void exitEqRelExpression(@NotNull GSQLParser.EqRelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#eqOp}.
	 * @param ctx the parse tree
	 */
	void enterEqOp(@NotNull GSQLParser.EqOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#eqOp}.
	 * @param ctx the parse tree
	 */
	void exitEqOp(@NotNull GSQLParser.EqOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void enterDropTable(@NotNull GSQLParser.DropTableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#dropTable}.
	 * @param ctx the parse tree
	 */
	void exitDropTable(@NotNull GSQLParser.DropTableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#useDatabase}.
	 * @param ctx the parse tree
	 */
	void enterUseDatabase(@NotNull GSQLParser.UseDatabaseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#useDatabase}.
	 * @param ctx the parse tree
	 */
	void exitUseDatabase(@NotNull GSQLParser.UseDatabaseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#orOp}.
	 * @param ctx the parse tree
	 */
	void enterOrOp(@NotNull GSQLParser.OrOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#orOp}.
	 * @param ctx the parse tree
	 */
	void exitOrOp(@NotNull GSQLParser.OrOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code actionAlterTable}
	 * labeled alternative in {@link GSQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void enterActionAlterTable(@NotNull GSQLParser.ActionAlterTableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code actionAlterTable}
	 * labeled alternative in {@link GSQLParser#alterTable}.
	 * @param ctx the parse tree
	 */
	void exitActionAlterTable(@NotNull GSQLParser.ActionAlterTableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code relSumExpression}
	 * labeled alternative in {@link GSQLParser#relExpression}.
	 * @param ctx the parse tree
	 */
	void enterRelSumExpression(@NotNull GSQLParser.RelSumExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code relSumExpression}
	 * labeled alternative in {@link GSQLParser#relExpression}.
	 * @param ctx the parse tree
	 */
	void exitRelSumExpression(@NotNull GSQLParser.RelSumExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void enterUpdateSet(@NotNull GSQLParser.UpdateSetContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#updateSet}.
	 * @param ctx the parse tree
	 */
	void exitUpdateSet(@NotNull GSQLParser.UpdateSetContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#tableInstruction}.
	 * @param ctx the parse tree
	 */
	void enterTableInstruction(@NotNull GSQLParser.TableInstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#tableInstruction}.
	 * @param ctx the parse tree
	 */
	void exitTableInstruction(@NotNull GSQLParser.TableInstructionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code actionAddConstraint}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void enterActionAddConstraint(@NotNull GSQLParser.ActionAddConstraintContext ctx);
	/**
	 * Exit a parse tree produced by the {@code actionAddConstraint}
	 * labeled alternative in {@link GSQLParser#action}.
	 * @param ctx the parse tree
	 */
	void exitActionAddConstraint(@NotNull GSQLParser.ActionAddConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link GSQLParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(@NotNull GSQLParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GSQLParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(@NotNull GSQLParser.LiteralContext ctx);
}