// Generated from FlaskParser.g4 by ANTLR 4.13.2

package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FlaskParser}.
 */
public interface FlaskParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FlaskParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(FlaskParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link FlaskParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(FlaskParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(FlaskParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(FlaskParser.ExprStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyLine}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterEmptyLine(FlaskParser.EmptyLineContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyLine}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitEmptyLine(FlaskParser.EmptyLineContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(FlaskParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(FlaskParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToTerm}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterToTerm(FlaskParser.ToTermContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToTerm}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitToTerm(FlaskParser.ToTermContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToFactor}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 */
	void enterToFactor(FlaskParser.ToFactorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToFactor}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 */
	void exitToFactor(FlaskParser.ToFactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(FlaskParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(FlaskParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ToAtom}
	 * labeled alternative in {@link FlaskParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterToAtom(FlaskParser.ToAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ToAtom}
	 * labeled alternative in {@link FlaskParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitToAtom(FlaskParser.ToAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IntAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIntAtom(FlaskParser.IntAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IntAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIntAtom(FlaskParser.IntAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FloatAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterFloatAtom(FlaskParser.FloatAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FloatAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitFloatAtom(FlaskParser.FloatAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterIdAtom(FlaskParser.IdAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitIdAtom(FlaskParser.IdAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(FlaskParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(FlaskParser.ParenExprContext ctx);
}