// Generated from FlaskParser.g4 by ANTLR 4.13.2

package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FlaskParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FlaskParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link FlaskParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(FlaskParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExprStatement}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(FlaskParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code EmptyLine}
	 * labeled alternative in {@link FlaskParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyLine(FlaskParser.EmptyLineContext ctx);
	/**
	 * Visit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSub(FlaskParser.AddSubContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToTerm}
	 * labeled alternative in {@link FlaskParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToTerm(FlaskParser.ToTermContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToFactor}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToFactor(FlaskParser.ToFactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link FlaskParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDiv(FlaskParser.MulDivContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ToAtom}
	 * labeled alternative in {@link FlaskParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitToAtom(FlaskParser.ToAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IntAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntAtom(FlaskParser.IntAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code FloatAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatAtom(FlaskParser.FloatAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code IdAtom}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdAtom(FlaskParser.IdAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParenExpr}
	 * labeled alternative in {@link FlaskParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(FlaskParser.ParenExprContext ctx);
}